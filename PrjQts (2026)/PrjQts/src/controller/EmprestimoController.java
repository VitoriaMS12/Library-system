package controller;

import dao.EmprestimoDAO;
import dao.LivroDAO;
import dao.UsuarioDAO;
import model.EmprestimoModel;
import model.LivroModel;
import model.UsuarioModel;

import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoController {
    private EmprestimoDAO emprestimoDAO;
    private LivroDAO livroDAO;
    private UsuarioDAO usuarioDAO;

    public EmprestimoController() {
        this.emprestimoDAO = new EmprestimoDAO();
        this.livroDAO = new LivroDAO();
        this.usuarioDAO = new UsuarioDAO();
    }

    public List<LivroModel> obterLivrosDisponiveis() {
        List<LivroModel> todos = livroDAO.listar();
        List<LivroModel> disponiveis = new ArrayList<>();
        for (LivroModel l : todos) {
            if (l.getQuantidadeDisponivel() > 0) {
                disponiveis.add(l);
            }
        }
        return disponiveis;
    }

    public List<UsuarioModel> obterUsuariosAtivos() {
        List<UsuarioModel> todos = usuarioDAO.listar();
        List<UsuarioModel> ativos = new ArrayList<>();
        for (UsuarioModel u : todos) {
            if ("Ativo".equalsIgnoreCase(u.getStatus())) {
                ativos.add(u);
            }
        }
        return ativos;
    }

    public String realizarEmprestimo(int idLivro, int idUsuario, int diasPrazo) {
        if (idLivro <= 0 || idUsuario <= 0) {
            return "Selecione um livro e um usuário válidos.";
        }

        LocalDate hoje = LocalDate.now();
        LocalDate devolucao = hoje.plusDays(diasPrazo);

        EmprestimoModel emp = new EmprestimoModel();
        emp.setIdLivro(idLivro);
        emp.setIdUsuario(idUsuario);
        emp.setDataEmprestimo(Date.valueOf(hoje));
        emp.setDataDevolucaoPrevista(Date.valueOf(devolucao));

        boolean ok = emprestimoDAO.registrarEmprestimo(emp);
        return ok ? "SUCESSO" : "Não foi possível realizar o empréstimo. Verifique o estoque do livro.";
    }

    public String realizarDevolucao(String idEmpStr, String idLivroStr) {
        try {
            int idEmp = Integer.parseInt(idEmpStr.trim());
            int idLivro = Integer.parseInt(idLivroStr.trim());

            boolean ok = emprestimoDAO.registrarDevolucao(idEmp, idLivro, Date.valueOf(LocalDate.now()));
            return ok ? "SUCESSO" : "Este empréstimo já foi devolvido ou não existe.";
        } catch (NumberFormatException e) {
            return "Selecione um empréstimo válido na tabela.";
        }
    }

    public List<Object[]> listarParaTabela() {
        List<EmprestimoModel> lista = emprestimoDAO.listar();
        List<Object[]> dados = new ArrayList<>();
        for (EmprestimoModel e : lista) {
            dados.add(new Object[]{
                e.getId(),
                e.getTituloLivro(),
                e.getNomeUsuario(),
                e.getDataEmprestimo(),
                e.getDataDevolucaoPrevista(),
                e.getDataDevolucaoReal() != null ? e.getDataDevolucaoReal() : "Pendente",
                e.getStatus(),
                e.getIdLivro()
            });
        }
        return dados;
    }
}