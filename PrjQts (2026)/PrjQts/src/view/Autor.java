package view;

import controller.AutorController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Autor extends JDialog {
    private JTextField txtId, txtNome, txtSobrenome, txtNacionalidade;
    private JTable tblAutores;
    private DefaultTableModel modeloTabela;
    private JButton btnCadastrar, btnConsultar, btnAtualizar, btnExcluir, btnSair;
    private AutorController controller;

    public Autor(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        controller = new AutorController();

        setSize(700, 480);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtSobrenome = new JTextField();
        txtNacionalidade = new JTextField();

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
        txtId.setBounds(120, 20, 60, 25);
        add(txtId);

        JLabel lblNome = new JLabel("Nome:");
        lblNome.setBounds(30, 55, 80, 25);
        add(lblNome);
        txtNome.setBounds(120, 55, 250, 25);
        add(txtNome);

        JLabel lblSobrenome = new JLabel("Sobrenome:");
        lblSobrenome.setBounds(30, 90, 80, 25);
        add(lblSobrenome);
        txtSobrenome.setBounds(120, 90, 250, 25);
        add(txtSobrenome);

        JLabel lblNacionalidade = new JLabel("Nacionalidade:");
        lblNacionalidade.setBounds(30, 125, 90, 25);
        add(lblNacionalidade);
        txtNacionalidade.setBounds(120, 125, 250, 25);
        add(txtNacionalidade);
    }

    private void configurarTabela() {
        String[] colunas = {"ID", "Nome", "Sobrenome", "Nacionalidade"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblAutores = new JTable(modeloTabela);
        tblAutores.setAutoCreateRowSorter(true);
        tblAutores.getTableHeader().setReorderingAllowed(false);

        JScrollPane scp = new JScrollPane(tblAutores);
        scp.setBounds(30, 165, 620, 200);
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
        btnSair.setBounds(550, 380, 100, 30);
        btnSair.addActionListener(e -> dispose());
        add(btnSair);
    }

    private void configurarAcoes() {
        tblAutores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tblAutores.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
                    txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
                    txtSobrenome.setText(modeloTabela.getValueAt(linha, 2) != null ? modeloTabela.getValueAt(linha, 2).toString() : "");
                    txtNacionalidade.setText(modeloTabela.getValueAt(linha, 3) != null ? modeloTabela.getValueAt(linha, 3).toString() : "");
                }
            }
        });

        btnCadastrar.addActionListener(e -> {
            String res = controller.cadastrar(txtNome.getText(), txtSobrenome.getText(), txtNacionalidade.getText());
            processarResposta(res, "Autor cadastrado!");
        });

        btnConsultar.addActionListener(e -> {
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Lista recarregada.");
        });

        btnAtualizar.addActionListener(e -> {
            String res = controller.atualizar(txtId.getText(), txtNome.getText(), txtSobrenome.getText(), txtNacionalidade.getText());
            processarResposta(res, "Autor atualizado!");
        });

        btnExcluir.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um autor.");
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Excluir autor?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                String res = controller.excluir(txtId.getText());
                processarResposta(res, "Autor excluído!");
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
        txtSobrenome.setText("");
        txtNacionalidade.setText("");
        tblAutores.clearSelection();
    }
}