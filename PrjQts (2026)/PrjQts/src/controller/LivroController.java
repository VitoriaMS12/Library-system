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


    public String cadastrar(String titulo, String isbn, String anoPub, String editora, String qtdStr) {

        if (titulo == null || titulo.trim().isEmpty()) {
            return "O título do livro é obrigatório.";
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            return "O ISBN do livro é obrigatório.";
        }

        if (anoPub == null || anoPub.trim().isEmpty()) {
            return "O ano de publicação é obrigatório.";
        }

        if (editora == null || editora.trim().isEmpty()) {
            return "A editora é obrigatória.";
        }

        if (qtdStr == null || qtdStr.trim().isEmpty()) {
            return "A quantidade é obrigatória.";
        }

        try {
            int qtd = Integer.parseInt(qtdStr.trim());

            if (qtd <= 0) {
                return "A quantidade deve ser maior que zero.";
            }

            // A quantidade disponível começa igual à quantidade cadastrada
            int qtdDisp = qtd;

            LivroModel livro = new LivroModel(
                0,
                titulo.trim(),
                isbn.trim(),
                anoPub.trim(),
                editora.trim(),
                qtd,
                qtdDisp
            );

            if (livroDAO.inserir(livro)) {
                return "SUCESSO";
            } else {
                return "Erro ao cadastrar livro no banco de dados.";
            }

        } catch (NumberFormatException e) {
            return "A quantidade deve ser um número inteiro válido.";
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
    
    public List<Object[]> consultarParaTabela(
        String titulo,
        String isbn,
        String anoPub,
        String editora) {

    List<LivroModel> lista = livroDAO.consultar(
        titulo,
        isbn,
        anoPub,
        editora
    );

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

    public String atualizar(String idStr, String titulo, String isbn, String anoPub, String editora, String qtdStr) {

        if (idStr == null || idStr.trim().isEmpty()) {
            return "Selecione um registro na tabela para atualizar.";
        }

        if (titulo == null || titulo.trim().isEmpty()) {
            return "O título do livro é obrigatório.";
        }

        if (isbn == null || isbn.trim().isEmpty()) {
            return "O ISBN do livro é obrigatório.";
        }

        if (anoPub == null || anoPub.trim().isEmpty()) {
            return "O ano de publicação é obrigatório.";
        }

        if (editora == null || editora.trim().isEmpty()) {
            return "A editora é obrigatória.";
        }

        if (qtdStr == null || qtdStr.trim().isEmpty()) {
            return "A quantidade é obrigatória.";
        }


        try {
            int id = Integer.parseInt(idStr.trim());
            int novaQtd = Integer.parseInt(qtdStr.trim());

            if (novaQtd <= 0) {
                return "A quantidade deve ser maior que zero.";
            }

            // Busca os dados atuais do livro.
            LivroModel livroAtual = livroDAO.buscarPorId(id);
            
            if (livroAtual == null) {
                return "Livro não encontrado";
            }
            
            //Quantidade de livros que estão emprestados
            int emprestados = livroAtual.getQuantidade() - livroAtual.getQuantidadeDisponivel();
            
            // Não permite que a nova quantidade seja menor que a quantidade que já está emprestada.
            if (novaQtd < emprestados) {
                return "A quantidade não pode ser menor que a quantidade de livros emprestados.";
            }
            
            // Mantém a quantidade de livros emprestados.
            int novaQtdDisp = novaQtd - emprestados;
            
            LivroModel livro = new LivroModel (
            id,
            titulo.trim(),
            isbn.trim(),
            anoPub.trim(),
            editora.trim(),
            novaQtd,
            novaQtdDisp
            );
            
            if (livroDAO.atualizar(livro)) {
                return "SUCESSO!";
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