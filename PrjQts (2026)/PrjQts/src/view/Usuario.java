package view;

import controller.UsuarioController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Usuario extends JDialog {
    private JTextField txtId, txtNome, txtSobrenome, txtEmail, txtTelefone;
    private JComboBox<String> cbxStatus;
    private JComboBox<String> cbxStatusConsulta;
    private JTable tblUsuarios;
    private DefaultTableModel modeloTabela;
    private JButton btnCadastrar, btnConsultar, btnAtualizar, btnExcluir, btnSair;
    private UsuarioController controller;

    public Usuario(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        controller = new UsuarioController();

        setSize(780, 520);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        txtId = new JTextField();
        txtId.setEditable(false);
        txtNome = new JTextField();
        txtSobrenome = new JTextField();
        txtEmail = new JTextField();
        txtTelefone = new JTextField();
        cbxStatus = new JComboBox<>(new String[]{"Ativo", "Inativo", "Bloqueado"});
        cbxStatusConsulta = new JComboBox<>(new String[]{"Todos", "Ativo", "Inativo", "Bloqueado"});

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
        txtNome.setBounds(110, 55, 220, 25);
        add(txtNome);

        JLabel lblSobrenome = new JLabel("Sobrenome:");
        lblSobrenome.setBounds(350, 55, 80, 25);
        add(lblSobrenome);
        txtSobrenome.setBounds(440, 55, 220, 25);
        add(txtSobrenome);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setBounds(30, 90, 80, 25);
        add(lblEmail);
        txtEmail.setBounds(110, 90, 220, 25);
        add(txtEmail);

        JLabel lblTel = new JLabel("Telefone:");
        lblTel.setBounds(350, 90, 80, 25);
        add(lblTel);
        txtTelefone.setBounds(440, 90, 220, 25);
        add(txtTelefone);

        JLabel lblStatus = new JLabel("Status:");
        lblStatus.setBounds(30, 125, 80, 25);
        add(lblStatus);
        cbxStatus.setBounds(110, 125, 120, 25);
        add(cbxStatus);
        
        JLabel lblStatusConsulta = new JLabel("Consultar por status:");
        lblStatusConsulta.setBounds(350, 125, 140, 25);
        add(lblStatusConsulta);
        cbxStatusConsulta.setBounds(490, 125, 120, 25);
        add(cbxStatusConsulta);
    }

    private void configurarTabela() {
        String[] colunas = {"ID", "Nome", "Sobrenome", "Email", "Telefone", "Dt. Cadastro", "Status"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblUsuarios = new JTable(modeloTabela);
        tblUsuarios.setAutoCreateRowSorter(true);
        tblUsuarios.getTableHeader().setReorderingAllowed(false);

        JScrollPane scp = new JScrollPane(tblUsuarios);
        scp.setBounds(30, 165, 700, 210);
        add(scp);
    }

    private void configurarBotoes() {
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(30, 400, 100, 30);
        add(btnCadastrar);

        btnConsultar = new JButton("Consultar");
        btnConsultar.setBounds(140, 400, 100, 30);
        add(btnConsultar);

        btnAtualizar = new JButton("Atualizar");
        btnAtualizar.setBounds(250, 400, 100, 30);
        add(btnAtualizar);

        btnExcluir = new JButton("Excluir");
        btnExcluir.setBounds(360, 400, 100, 30);
        add(btnExcluir);

        btnSair = new JButton("Sair");
        btnSair.setBounds(630, 400, 100, 30);
        btnSair.addActionListener(e -> dispose());
        add(btnSair);
    }

    private void configurarAcoes() {
        tblUsuarios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tblUsuarios.getSelectedRow();
                if (linha != -1) {
                    txtId.setText(modeloTabela.getValueAt(linha, 0).toString());
                    txtNome.setText(modeloTabela.getValueAt(linha, 1).toString());
                    txtSobrenome.setText(modeloTabela.getValueAt(linha, 2) != null ? modeloTabela.getValueAt(linha, 2).toString() : "");
                    txtEmail.setText(modeloTabela.getValueAt(linha, 3) != null ? modeloTabela.getValueAt(linha, 3).toString() : "");
                    txtTelefone.setText(modeloTabela.getValueAt(linha, 4) != null ? modeloTabela.getValueAt(linha, 4).toString() : "");
                    if (modeloTabela.getValueAt(linha, 6) != null) {
                        cbxStatus.setSelectedItem(modeloTabela.getValueAt(linha, 6).toString());
                    }
                }
            }
        });

        btnCadastrar.addActionListener(e -> {
            String res = controller.cadastrar(
                txtNome.getText(), 
                txtSobrenome.getText(), 
                txtEmail.getText(), 
                txtTelefone.getText(), 
                cbxStatus.getSelectedItem().toString()
            );
            processarResposta(res, "Usuário cadastrado!");
        });

        btnConsultar.addActionListener(e -> {
            String nome = txtNome.getText().trim();
            String sobrenome = txtSobrenome.getText().trim();
            String email = txtEmail.getText().trim();
            String telefone = txtTelefone.getText().trim();
            
            String status = cbxStatusConsulta.getSelectedItem().toString();
            
            if (status.equals("Todos")) {
                status = "";
            }
            
            
            
            modeloTabela.setRowCount(0);
            
            List<Object[]> dados = controller.consultarParaTabela(nome, sobrenome, email, telefone, status);
            
            for (Object[] linha : dados) {
                modeloTabela.addRow(linha);
            }
            
            if (dados.isEmpty()) {
                JOptionPane.showMessageDialog(
                this,"Nenhum usuário encontrado.");
            }
            
            limparCampos();
        });

        btnAtualizar.addActionListener(e -> {
            String res = controller.atualizar(
                txtId.getText(), txtNome.getText(), txtSobrenome.getText(), txtEmail.getText(), txtTelefone.getText(), cbxStatus.getSelectedItem().toString()
            );
            processarResposta(res, "Usuário atualizado!");
        });

        btnExcluir.addActionListener(e -> {
            if (txtId.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um usuário.");
                return;
            }
            if (JOptionPane.showConfirmDialog(this, "Excluir usuário?", "Confirmação", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION) {
                String res = controller.excluir(txtId.getText());
                processarResposta(res, "Usuário excluído!");
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
        txtEmail.setText("");
        txtTelefone.setText("");
        cbxStatus.setSelectedIndex(0);
        cbxStatusConsulta.setSelectedIndex(0);
        tblUsuarios.clearSelection();
    }
}