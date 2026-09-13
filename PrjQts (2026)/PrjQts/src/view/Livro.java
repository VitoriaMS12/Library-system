package view;

import controller.LivroController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.text.ParseException;
import java.util.List;

public class Livro extends JDialog {
    private JTextField txtId, txtTitulo, txtEditora;
    private JFormattedTextField txtIsbn, txtAnoPub, txtQtd, txtQtdDisp;
    private MaskFormatter maskIsbn, maskAno, maskQtd;

    private JTable tblLivros;
    private DefaultTableModel modeloTabela;
    private JScrollPane scpTabela;

    private JButton btnCadastrar, btnConsultar, btnAtualizar, btnExcluir, btnSair;

    private LivroController controller;

    public Livro(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        
        controller = new LivroController();

        setSize(800, 520);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtTitulo = new JTextField();
        txtEditora = new JTextField();

        try {
            maskIsbn = new MaskFormatter("###-##-#####-##-#");
            maskIsbn.setValueContainsLiteralCharacters(false);

            maskAno = new MaskFormatter("####");
            maskAno.setValueContainsLiteralCharacters(false);

            maskQtd = new MaskFormatter("#####");
            maskQtd.setValueContainsLiteralCharacters(false);
        } catch (ParseException e) {
            JOptionPane.showMessageDialog(this, "Erro nas máscaras: " + e.getMessage());
        }

        txtIsbn = new JFormattedTextField(maskIsbn);
        txtAnoPub = new JFormattedTextField(maskAno);
        txtQtd = new JFormattedTextField(maskQtd);
        txtQtdDisp = new JFormattedTextField(maskQtd);

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

        JLabel lblTitulo = new JLabel("Título:");
        lblTitulo.setBounds(30, 55, 80, 25);
        add(lblTitulo);
        txtTitulo.setBounds(120, 55, 300, 25);
        add(txtTitulo);

        JLabel lblIsbn = new JLabel("ISBN:");
        lblIsbn.setBounds(30, 90, 80, 25);
        add(lblIsbn);
        txtIsbn.setBounds(120, 90, 150, 25);
        add(txtIsbn);

        JLabel lblAno = new JLabel("Ano Pub.:");
        lblAno.setBounds(300, 90, 80, 25);
        add(lblAno);
        txtAnoPub.setBounds(370, 90, 50, 25);
        add(txtAnoPub);

        JLabel lblEditora = new JLabel("Editora:");
        lblEditora.setBounds(30, 125, 80, 25);
        add(lblEditora);
        txtEditora.setBounds(120, 125, 300, 25);
        add(txtEditora);

        JLabel lblQtd = new JLabel("Qtd Total:");
        lblQtd.setBounds(450, 55, 80, 25);
        add(lblQtd);
        txtQtd.setBounds(540, 55, 60, 25);
        add(txtQtd);

        JLabel lblQtdDisp = new JLabel("Qtd Disp.:");
        lblQtdDisp.setBounds(450, 90, 80, 25);
        add(lblQtdDisp);
        txtQtdDisp.setBounds(540, 90, 60, 25);
        add(txtQtdDisp);
    }

    private void configurarTabela() {
        String[] colunas = {
            "ID", "Título", "ISBN", "Ano Pub.", "Editora", "Qtd Total", "Qtd Disp."
        };

        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblLivros = new JTable(modeloTabela);
        tblLivros.setAutoCreateRowSorter(true);
        tblLivros.getTableHeader().setReorderingAllowed(false);

        scpTabela = new JScrollPane(tblLivros);
        scpTabela.setBounds(30, 170, 720, 230);
        add(scpTabela);
    }

    private void configurarBotoes() {
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(30, 420, 110, 30);
        add(btnCadastrar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(150, 420, 110, 30);
        add(btnConsultar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(270, 420, 110, 30);
        add(btnAtualizar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(390, 420, 110, 30);
        add(btnExcluir);

        btnSair = new JButton("Sair");
        btnSair.setBounds(640, 420, 110, 30);
        btnSair.addActionListener(e -> dispose());
        add(btnSair);
    }

    private void configurarAcoes() {
        tblLivros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tblLivros.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
                    txtTitulo.setText(modeloTabela.getValueAt(linha, 1).toString());
                    txtIsbn.setText(modeloTabela.getValueAt(linha, 2).toString());
                    txtAnoPub.setText(modeloTabela.getValueAt(linha, 3).toString());
                    txtEditora.setText(modeloTabela.getValueAt(linha, 4).toString());
                    txtQtd.setText(modeloTabela.getValueAt(linha, 5).toString());
                    txtQtdDisp.setText(modeloTabela.getValueAt(linha, 6).toString());
                }
            }
        });

        btnCadastrar.addActionListener(e -> {
            String resp = controller.cadastrar(
                txtTitulo.getText(),
                txtIsbn.getText(),
                txtAnoPub.getText(),
                txtEditora.getText(),
                txtQtd.getText(),
                txtQtdDisp.getText()
            );

            if ("SUCESSO".equals(resp)) {
                JOptionPane.showMessageDialog(this, "Livro cadastrado com sucesso!");
                atualizarTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, resp, "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnConsultar.addActionListener(e -> {
            atualizarTabela();
            limparCampos();
            JOptionPane.showMessageDialog(this, "Tabela de livros recarregada.");
        });

        btnAtualizar.addActionListener(e -> {
            String resp = controller.atualizar(
                txtId.getText(),
                txtTitulo.getText(),
                txtIsbn.getText(),
                txtAnoPub.getText(),
                txtEditora.getText(),
                txtQtd.getText(),
                txtQtdDisp.getText()
            );

            if ("SUCESSO".equals(resp)) {
                JOptionPane.showMessageDialog(this, "Livro atualizado com sucesso!");
                atualizarTabela();
                limparCampos();
            } else {
                JOptionPane.showMessageDialog(this, resp, "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnExcluir.addActionListener(e -> {
            if (txtId.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um registro na tabela para excluir.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            int confirma = JOptionPane.showConfirmDialog(this, "Deseja realmente excluir este livro?", "Confirmação", JOptionPane.YES_NO_OPTION);
            if (confirma == JOptionPane.YES_OPTION) {
                String resp = controller.excluir(txtId.getText());
                if ("SUCESSO".equals(resp)) {
                    JOptionPane.showMessageDialog(this, "Livro excluído com sucesso!");
                    atualizarTabela();
                    limparCampos();
                } else {
                    JOptionPane.showMessageDialog(this, resp, "Erro", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Object[]> dados = controller.listarParaTabela();
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
    }

    private void limparCampos() {
        txtId.setText("");
        txtTitulo.setText("");
        txtIsbn.setValue(null);
        txtAnoPub.setValue(null);
        txtEditora.setText("");
        txtQtd.setValue(null);
        txtQtdDisp.setValue(null);
        tblLivros.clearSelection();
    }
}