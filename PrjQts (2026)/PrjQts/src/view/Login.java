package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import controller.FuncionarioController;
import javax.swing.JOptionPane;

public class Login extends JFrame {
    
    private JLabel lblCodigo;
    private JLabel lblSenha;
    private JTextField txtCodigo;
    private JPasswordField txtSenha;
    private JButton btnEntrar;
    private JButton btnCadastrar;
    private FuncionarioController funcionarioController;

    public Login() {

        setTitle("Login - Biblioteca QTS");
        setSize(400, 300);
        setLayout(null);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(50, 50, 100, 25);
        add(lblCodigo);

        txtCodigo = new JTextField();
        txtCodigo.setBounds(150, 50, 180, 25);
        add(txtCodigo);

        lblSenha = new JLabel("Senha:");
        lblSenha.setBounds(50, 100, 100, 25);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(150, 100, 180, 25);
        add(txtSenha);

        btnEntrar = new JButton("Entrar");
        btnEntrar.setBounds(150, 160, 100, 30);
        add(btnEntrar);
        
        funcionarioController = new FuncionarioController();
        btnEntrar.setBounds(150, 160, 100, 30);
        add(btnEntrar);
        
        btnEntrar.addActionListener(e -> entrar());
        
        btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.setBounds(150, 205, 100, 30);
        add(btnCadastrar);

        btnCadastrar.addActionListener(e -> abrirCadastro());
    }
    
    private void entrar() {

        String codigo = txtCodigo.getText().trim();
        String senha = new String(txtSenha.getPassword());

        if (codigo.isEmpty() || senha.isEmpty()) {
            
            JOptionPane.showMessageDialog(
                    this,
                    "Preencha o código e a senha."
            );
            return;
        }

        boolean loginValido =
                funcionarioController.validarLogin(codigo, senha);

        if (loginValido) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Login realizado com sucesso!"
            );
            
            MainMenu menu = new MainMenu();
            menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            menu.setVisible(true);
            
            this.dispose();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "Código ou senha incorretos."
            );
        }
    }
    
    private void abrirCadastro() {

        CadastroFuncionario cadastro = new CadastroFuncionario();
        cadastro.setVisible(true);

        this.dispose();
    }
    
    public static void main(String[] args) {
        Login tela = new Login();
        tela.setVisible(true);
    }
}
