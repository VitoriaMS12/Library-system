package view;

import controller.FuncionarioController;
import model.FuncionarioModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class CadastroFuncionario extends JFrame {
    
    private JLabel lblNome;
    private JLabel lblSobrenome;
    private JLabel lblCodigo;
    private JLabel lblSenha;

    private JTextField txtNome;
    private JTextField txtSobrenome;
    private JTextField txtCodigo;
    private JPasswordField txtSenha;

    private JButton btnCadastrar;

    private FuncionarioController funcionarioController;
    
    public CadastroFuncionario() {

        funcionarioController = new FuncionarioController();

        setTitle("Cadastro de Funcionário");
        setSize(450, 350);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        lblNome = new JLabel("Nome:");
        lblNome.setBounds(50, 40, 100, 25);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(150, 40, 220, 25);
        add(txtNome);

        lblSobrenome = new JLabel("Sobrenome:");
        lblSobrenome.setBounds(50, 90, 100, 25);
        add(lblSobrenome);

        txtSobrenome = new JTextField();
        txtSobrenome.setBounds(150, 90, 220, 25);
        add(txtSobrenome);

        lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(50, 140, 100, 25);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(150, 140, 220, 25);
        add(txtCodigo);

        lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 190, 100, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(150, 190, 220, 25);
        add(txtSenha);

        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(150, 240, 120, 30);
        add(btnCadastrar);

        btnCadastrar.addActionListener(e -> cadastrar());
    }

    private void cadastrar() {

        String nome = txtNome.getText().trim();
        String sobrenome = txtSobrenome.getText().trim();
        String codigo = txtCodigo.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (nome.isEmpty()
                || sobrenome.isEmpty()
                || codigo.isEmpty()
                || senha.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Preencha todos os campos."
            );

            return;
        }

        FuncionarioModel funcionario = new FuncionarioModel();

        funcionario.setNome(nome);
        funcionario.setSobrenome(sobrenome);
        funcionario.setCodigo(codigo);
        funcionario.setSenha(senha);

        boolean cadastrado =
                funcionarioController.cadastrar(funcionario);

        if (cadastrado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Funcionário cadastrado com sucesso!"
            );
            
            Login login = new Login();
            login.setVisible(true);

            this.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível cadastrar o funcionário."
            );
        }
    }

    private void limparCampos() {

        txtNome.setText("");
        txtSobrenome.setText("");
        txtCodigo.setText("");
        txtSenha.setText("");
    }

    public static void main(String[] args) {

        CadastroFuncionario tela = new CadastroFuncionario();
        tela.setVisible(true);
    }
}
