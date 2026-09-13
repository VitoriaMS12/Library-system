package controller;

import dao.LivroDAO;
import model.LivroModel;
import java.util.ArrayList;
import java.util.List;

public class LivroController {

    private LivroDAO livroDAO;

    public LivroController() {
        this.livroDAO = new LivroDAO();
    }

    public String cadastrar(String titulo, String isbn, String anoPub, String editora, String qtdStr, String qtdDispStr) {
        if (titulo == null || titulo.trim().isEmpty()) {
            return "O título do livro é obrigatório.";
        }

        try {
            int qtd = Integer.parseInt(qtdStr.trim());
            int qtdDisp = Integer.parseInt(qtdDispStr.trim());

            LivroModel livro = new LivroModel(0, titulo, isbn, anoPub, editora, qtd, qtdDisp);
            if (livroDAO.inserir(livro)) {
                return "SUCESSO";
            } else {
                return "Erro ao cadastrar livro no banco de dados.";
            }
        } catch (NumberFormatException e) {
            return "Quantidade e Quantidade Disponível devem ser valores numéricos inteiros válidos.";
        }
    }

    public List<Object[]> listarParaTabela() {
        List<LivroModel> lista = livroDAO.listar();
        List<Object[]> dadosTabela = new ArrayList<>();

        for (LivroModel l : lista) {
            dadosTabela.add(new Object[]{
                l.getId(),
                l.getTitulo(),
                l.getIsbn(),
                l.getAnoPublicacao(),
                l.getEditora(),
                l.getQuantidade(),
                l.getQuantidadeDisponivel()
            });
        }
        return dadosTabela;
    }

    public String atualizar(String idStr, String titulo, String isbn, String anoPub, String editora, String qtdStr, String qtdDispStr) {
        if (idStr == null || idStr.trim().isEmpty()) {
            return "Selecione um registro na tabela para atualizar.";
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            int qtd = Integer.parseInt(qtdStr.trim());
            int qtdDisp = Integer.parseInt(qtdDispStr.trim());

            LivroModel livro = new LivroModel(id, titulo, isbn, anoPub, editora, qtd, qtdDisp);
            if (livroDAO.atualizar(livro)) {
                return "SUCESSO";
            } else {
                return "Erro ao atualizar livro no banco de dados.";
            }
        } catch (NumberFormatException e) {
            return "Preencha os campos numéricos corretamente.";
        }
    }

    public String excluir(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            if (livroDAO.excluir(id)) {
                return "SUCESSO";
            } else {
                return "Erro ao excluir o livro do banco de dados.";
            }
        } catch (NumberFormatException e) {
            return "ID inválido.";
        }
    }
}