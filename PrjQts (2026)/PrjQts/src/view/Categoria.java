package view;

import controller.CategoriaController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Categoria extends JDialog {
    private JTextField txtId, txtNome;
    private JTextArea txtDescricao;
    private JTable tblCategorias;
    private DefaultTableModel modeloTabela;
    private JButton btnCadastrar, btnConsultar, btnAtualizar, btnExcluir, btnSair;
    private CategoriaController controller;

    public Categoria(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        controller = new CategoriaController();

        setSize(650, 480);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtDescricao = new JTextArea();

        configurarFormulario();
        configurarTabela();
        configurarBotoes();
        configurarAcoes();

        atualizarTabela();
    }

    private void configurarFormulario() {
        JLabel lblId = new JLabel("ID:");
        lblId.setBounds(30, 20, 80, 25);
        add(lblId);
        txtId.setBounds(110, 20, 60, 25);
        add(txtId);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 55, 80, 25);
        add(lblNome);
        txtNome.setBounds(110, 55, 250, 25);
        add(txtNome);

        JLabel lblDesc = new JLabel("Descrição:");
        lblDesc.setBounds(30, 90, 80, 25);
        add(lblDesc);

        JScrollPane scpDesc = new JScrollPane(txtDescricao);
        scpDesc.setBounds(110, 90, 480, 50);
        add(scpDesc);
    }

    private void configurarTabela() {
        String[] colunas = {"ID", "Nome", "Descrição"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblCategorias = new JTable(modeloTabela);
        tblCategorias.setAutoCreateRowSorter(true);
        tblCategorias.getTableHeader().setReorderingAllowed(false);

        JScrollPane scp = new JScrollPane(tblCategorias);
        scp.setBounds(30, 155, 560, 200);
        add(scp);
    }

    private void configurarBotoes() {
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(30, 380, 100, 30);
        add(btnCadastrar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(140, 380, 100, 30);
        add(btnConsultar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(250, 380, 100, 30);
        add(btnAtualizar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(360, 380, 100, 30);
        add(btnExcluir);

        btnSair = new JButton("Sair");
        btnSair.setBounds(490, 380, 100, 30);
        btnSair.addActionListener(e -> dispose());
        add(btnSair);
    }

    private void configurarAcoes() {
        tblCategorias.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tblCategorias.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
                    txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
                    txtDescricao.setText(modeloTabela.getValueAt(linha, 2) != null ? modeloTabela.getValueAt(linha, 2).toString() : "");
                }
            }
        });

        btnCadastrar.addActionListener(e -> {
            String res = controller.cadastrar(txtNome.getText(), txtDescricao.getText());
            processarResposta(res, "Categoria cadastrada!");
        });

        btnConsultar.addActionListener(e -> {
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Lista recarregada.");
        });

        btnAtualizar.addActionListener(e -> {
            String res = controller.atualizar(txtId.getText(), txtNome.getText(), txtDescricao.getText());
            processarResposta(res, "Categoria atualizada!");
        });

        btnExcluir.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione uma categoria.");
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Excluir categoria?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                String res = controller.excluir(txtId.getText());
                processarResposta(res, "Categoria excluída!");
            }
        });
    }

    private void processarResposta(String res, String msgSucesso) {
        if ("SUCESSO".equals(res)) {
            JOptionPane.showMessageDialog(this, msgSucesso);
            atualizarTabela();
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(this, res, "Aviso", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Object[]> dados = controller.listarParaTabela();
        for (Object[] linha : dados) { modeloTabela.addRow(linha); }
    }

    private void limparCampos() {
        txtId.setText("");
        txtNome.setText("");
        txtDescricao.setText("");
        tblCategorias.clearSelection();
    }
}