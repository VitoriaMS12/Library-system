package view;

import controller.EmprestimoController;
import model.LivroModel;
import model.UsuarioModel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class Emprestimo extends JDialog {
    private JComboBox<ItemCombo> cbxLivros, cbxUsuarios;
    private JTextField txtDias;
    private JTable tblEmprestimos;
    private DefaultTableModel modeloTabela;
    private JButton btnEmprestar, btnDevolver, btnAtualizar, btnSair;

    private EmprestimoController controller;
    private String idEmprestimoSelecionado = "";
    private String idLivroSelecionado = "";

    // Classe auxiliar para armazenar ID + Texto nos ComboBoxes
    private static class ItemCombo {
        int id;
        String descricao;

        ItemCombo(int id, String descricao) {
            this.id = id;
            this.descricao = descricao;
        }

        @Override
        public String toString() {
            return descricao;
        }
    }

    public Emprestimo(Frame owner, String title, boolean modal) {
        super(owner, title, modal);
        controller = new EmprestimoController();

        setSize(850, 520);
        setLayout(null);
        setResizable(false);
        setLocationRelativeTo(owner);
        setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);

        cbxLivros = new JComboBox<>();
        cbxUsuarios = new JComboBox<>();
        txtDias = new JTextField("14"); // Prazo padrão de 14 dias

        configurarFormulario();
        configurarTabela();
        configurarBotoes();
        configurarAcoes();

        carregarCombos();
        atualizarTabela();
    }

    private void configurarFormulario() {
        JLabel lblLivro = new JLabel("Livro:");
        lblLivro.setBounds(30, 20, 80, 25);
        add(lblLivro);
        cbxLivros.setBounds(100, 20, 320, 25);
        add(cbxLivros);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setBounds(440, 20, 80, 25);
        add(lblUsuario);
        cbxUsuarios.setBounds(510, 20, 290, 25);
        add(cbxUsuarios);

        JLabel lblDias = new JLabel("Prazo (dias):");
        lblDias.setBounds(30, 60, 80, 25);
        add(lblDias);
        txtDias.setBounds(100, 60, 60, 25);
        add(txtDias);
    }

    private void configurarTabela() {
        String[] colunas = {"ID", "Livro", "Usuário", "Dt. Empréstimo", "Dt. Prevista", "Dt. Devolução", "Status", "idLivro"};
        modeloTabela = new DefaultTableModel(colunas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        tblEmprestimos = new JTable(modeloTabela);
        tblEmprestimos.setAutoCreateRowSorter(true);
        tblEmprestimos.getTableHeader().setReorderingAllowed(false);
        
        // Oculta a coluna idLivro usada internamente na devolução
        tblEmprestimos.removeColumn(tblEmprestimos.getColumnModel().getColumn(7));

        JScrollPane scp = new JScrollPane(tblEmprestimos);
        scp.setBounds(30, 100, 770, 290);
        add(scp);
    }

    private void configurarBotoes() {
        btnEmprestar = new JButton("Realizar Empréstimo");
        btnEmprestar.setBounds(30, 410, 160, 30);
        add(btnEmprestar);

        btnDevolver = new JButton("Devolver Livro");
        btnDevolver.setBounds(200, 410, 140, 30);
        add(btnDevolver);

        btnAtualizar = new JButton("Recarregar");
        btnAtualizar.setBounds(350, 410, 120, 30);
        add(btnAtualizar);

        btnSair = new JButton("Sair");
        btnSair.setBounds(680, 410, 120, 30);
        btnSair.addActionListener(e -> dispose());
        add(btnSair);
    }

    private void configurarAcoes() {
        tblEmprestimos.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int linha = tblEmprestimos.getSelectedRow();
                if (linha != -1) {
                    idEmprestimoSelecionado = modeloTabela.getValueAt(linha, 0).toString();
                    idLivroSelecionado = modeloTabela.getValueAt(linha, 7).toString();
                }
            }
        });

        btnEmprestar.addActionListener(e -> {
            ItemCombo itemLivro = (ItemCombo) cbxLivros.getSelectedItem();
            ItemCombo itemUsuario = (ItemCombo) cbxUsuarios.getSelectedItem();

            if (itemLivro == null || itemUsuario == null) {
                JOptionPane.showMessageDialog(this, "Selecione um livro e um usuário.", "Aviso", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                int dias = Integer.parseInt(txtDias.getText().trim());
                String res = controller.realizarEmprestimo(itemLivro.id, itemUsuario.id, dias);

                if ("SUCESSO".equals(res)) {
                    JOptionPane.showMessageDialog(this, "Empréstimo registrado com sucesso!");
                    carregarCombos();
                    atualizarTabela();
                } else {
                    JOptionPane.showMessageDialog(this, res, "Aviso", JOptionPane.WARNING_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Informe um prazo numérico válido em dias.", "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnDevolver.addActionListener(e -> {
            if (idEmprestimoSelecionado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Selecione um empréstimo na tabela para devolver.");
                return;
            }

            String res = controller.realizarDevolucao(idEmprestimoSelecionado, idLivroSelecionado);
            if ("SUCESSO".equals(res)) {
                JOptionPane.showMessageDialog(this, "Devolução registrada!");
                idEmprestimoSelecionado = "";
                idLivroSelecionado = "";
                carregarCombos();
                atualizarTabela();
            } else {
                JOptionPane.showMessageDialog(this, res, "Aviso", JOptionPane.WARNING_MESSAGE);
            }
        });

        btnAtualizar.addActionListener(e -> {
            carregarCombos();
            atualizarTabela();
        });
    }

    private void carregarCombos() {
        cbxLivros.removeAllItems();
        cbxUsuarios.removeAllItems();

        List<LivroModel> livros = controller.obterLivrosDisponiveis();
        for (LivroModel l : livros) {
            cbxLivros.addItem(new ItemCombo(l.getId(), l.getTitulo() + " (Disp: " + l.getQuantidadeDisponivel() + ")"));
        }

        List<UsuarioModel> usuarios = controller.obterUsuariosAtivos();
        for (UsuarioModel u : usuarios) {
            cbxUsuarios.addItem(new ItemCombo(u.getId(), u.getNome() + " " + (u.getSobrenome() != null ? u.getSobrenome() : "")));
        }
    }

    private void atualizarTabela() {
        modeloTabela.setRowCount(0);
        List<Object[]> dados = controller.listarParaTabela();
        for (Object[] linha : dados) {
            modeloTabela.addRow(linha);
        }
    }
}