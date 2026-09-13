package view; // Eduardo Nonato - 3ºDS 😁 feat. Duda

import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private JLabel titulo;
    private JButton btnLivro, btnAutor, btnUsuario, btnCategoria, btnSair, btnEmprestimo;

    public MainMenu() {
        super("Menu");
        Container tela = getContentPane();
        tela.setBackground(new Color(99, 40, 50));
        setLayout(null);

        titulo = new JLabel("Sistema de Biblioteca", SwingConstants.CENTER);
        titulo.setForeground(Color.white);
        titulo.setFont(new Font("Times New Roman", Font.BOLD, 24));
        titulo.setBounds(78, 40, 240, 30);
        titulo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        tela.add(titulo);

        // Botão Livros
        btnLivro = new JButton("Livros");
        btnLivro.setForeground(Color.white);
        btnLivro.setBackground(Color.black);
        btnLivro.setBounds(50, 100, 100, 25);
        btnLivro.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        btnLivro.addActionListener(e -> {
            Livro janelaLivro = new Livro(this, "Gerenciamento de Livros", true);
            janelaLivro.setVisible(true);
        });
        tela.add(btnLivro);

        // Botão Autores
        btnAutor = new JButton("Autores");
        btnAutor.setForeground(Color.white);
        btnAutor.setBackground(Color.black);
        btnAutor.setBounds(50, 140, 100, 25);
        btnAutor.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        btnAutor.addActionListener(e -> {
            Autor janelaAutor = new Autor(this, "Gerenciamento de Autores", true);
            janelaAutor.setVisible(true);
        });
        tela.add(btnAutor);

        // Botão Usuário
        btnUsuario = new JButton("Usuário");
        btnUsuario.setForeground(Color.white);
        btnUsuario.setBackground(Color.black);
        btnUsuario.setBounds(50, 180, 100, 25);
        btnUsuario.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        btnUsuario.addActionListener(e -> {
            Usuario janelaUsuario = new Usuario(this, "Gerenciamento de Usuários", true);
            janelaUsuario.setVisible(true);
        });
        tela.add(btnUsuario);

        // Botão Categorias
        btnCategoria = new JButton("Categorias");
        btnCategoria.setForeground(Color.white);
        btnCategoria.setBackground(Color.black);
        btnCategoria.setBounds(236, 100, 100, 25);
        btnCategoria.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        btnCategoria.addActionListener(e -> {
            Categoria janelaCategoria = new Categoria(this, "Gerenciamento de Categorias", true);
            janelaCategoria.setVisible(true);
        });
        tela.add(btnCategoria);
        
        btnEmprestimo = new JButton("Empréstimos");
        btnEmprestimo.setForeground(Color.white);
        btnEmprestimo.setBackground(Color.black);
        btnEmprestimo.setBounds(236, 180, 100, 25);
        btnEmprestimo.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        btnEmprestimo.addActionListener(e -> {
            Emprestimo janela = new Emprestimo(this, "Gerenciamento de Empréstimos", true);
            janela.setVisible(true);
        });        
        tela.add(btnEmprestimo);

        // Botão Sair
        btnSair = new JButton("Sair");
        btnSair.setForeground(Color.white);
        btnSair.setBackground(Color.black);
        btnSair.setBounds(236, 140, 100, 25);
        btnSair.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1, true));
        btnSair.addActionListener(e -> {
            int op = JOptionPane.showConfirmDialog(this, "Deseja sair?", "Mensagem do Programa", JOptionPane.YES_NO_OPTION);
            if (op == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        });
        tela.add(btnSair);

        setSize(400, 300);
        setLocationRelativeTo(null);
        setResizable(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainMenu app = new MainMenu();
            app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            app.setVisible(true);
        });
    }
}