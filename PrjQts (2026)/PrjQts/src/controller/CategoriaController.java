package controller;

import dao.CategoriaDAO;
import model.CategoriaModel;
import java.util.ArrayList;
import java.util.List;

public class CategoriaController {
    private CategoriaDAO categoriaDAO;

    public CategoriaController() {
        this.categoriaDAO = new CategoriaDAO();
    }

    public String cadastrar(String nome, String descricao) {
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome da categoria é obrigatório.";
        }
        if (descricao == null || descricao.trim().isEmpty()) {
            return "A descrição da categoria  é obrigatória.";
        }
                
        CategoriaModel cat = new CategoriaModel(0, nome.trim(), descricao.trim());
        return categoriaDAO.inserir(cat) ? "SUCESSO" : "Erro ao cadastrar categoria.";
    }

    public List<Object[]> listarParaTabela() {
        List<CategoriaModel> lista = categoriaDAO.listar();
        List<Object[]> dados = new ArrayList<>();
        for (CategoriaModel c : lista) {
            dados.add(new Object[]{ c.getId(), c.getNome(), c.getDescricao() });
        }
        return dados;
    }
    
    public List<Object[]> consultarParaTabela(
        String nome) {

    List<CategoriaModel> lista = categoriaDAO.consultar(
        nome
    );

    List<Object[]> dadosTabela = new ArrayList<>();

    for (CategoriaModel l : lista) {
        dadosTabela.add(new Object[]{
            l.getId(),
            l.getNome(),
            l.getDescricao()
        });
    }

    return dadosTabela;
}

    public String atualizar(String idStr, String nome, String descricao) {
        if (idStr == null || idStr.trim().isEmpty()) 
            return "Selecione uma categoria.";
        
        if (nome == null || nome.trim().isEmpty()) 
            return "O nome é obrigatório.";
        
        if (descricao == null || descricao.trim().isEmpty()) {
            return "A descrição é obrigatória.";
        }

        try {
            int id = Integer.parseInt(idStr.trim());
            
            CategoriaModel cat = new CategoriaModel(id, nome.trim(), descricao.trim());
            return categoriaDAO.atualizar(cat) ? "SUCESSO" : "Erro ao atualizar categoria.";
        } catch (NumberFormatException e) {
            return "ID inválido.";
        }
    }

    public String excluir(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            return categoriaDAO.excluir(id) ? "SUCESSO" : "Erro ao excluir categoria.";
        } catch (NumberFormatException e) {
            return "ID inválido.";
        }
    }
}