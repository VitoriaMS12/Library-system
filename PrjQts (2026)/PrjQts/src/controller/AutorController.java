package controller;

import dao.AutorDAO;
import model.AutorModel;
import java.util.ArrayList;
import java.util.List;

public class AutorController {
    private AutorDAO autorDAO;

    public AutorController() {
        this.autorDAO = new AutorDAO();
    }

    public String cadastrar(String nome, String sobrenome, String nacionalidade) {
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome do autor é obrigatório.";
        }
        
        if (sobrenome == null || sobrenome.trim().isEmpty()) {
            return "O sobrenome do autor é obrigatório.";
        }
        
        AutorModel autor = new AutorModel(
                0, 
                nome.trim(), 
                sobrenome.trim(), 
                nacionalidade == null ? "" : nacionalidade.trim()
        );
        
        return autorDAO.inserir(autor) 
                ? "SUCESSO" 
                : "Erro ao cadastrar autor no banco de dados.";
    }

    public List<Object[]> listarParaTabela() {
        List<AutorModel> lista = autorDAO.listar();
        List<Object[]> dados = new ArrayList<>();
        for (AutorModel a : lista) {
            dados.add(new Object[]{ a.getId(), a.getNome(), a.getSobrenome(), a.getNacionalidade() });
        }
        return dados;
    }
    
    public List<Object[]> consultarParaTabela(
        String nome,
        String sobrenome,
        String nacionalidade) {

    List<AutorModel> lista = autorDAO.consultar(
        nome,
        sobrenome,
        nacionalidade
    );

    List<Object[]> dadosTabela = new ArrayList<>();

    for (AutorModel l : lista) {
        dadosTabela.add(new Object[]{
            l.getId(),
            l.getNome(),
            l.getSobrenome(),
            l.getNacionalidade()
        });
    }

    return dadosTabela;
}

    public String atualizar(String idStr, String nome, String sobrenome, String nacionalidade) {
        
        if (idStr == null || idStr.trim().isEmpty()) {
            return "Selecione um autor na tabela.";
        }
        
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome do autor é obrigatório.";
        }
        
        if (sobrenome == null || sobrenome.trim().isEmpty()) {
            return "O sobrenome do autor é obrigatório.";
        }
        
        try {
            int id = Integer.parseInt(idStr.trim());
            
            AutorModel autor = new AutorModel(
                    id, 
                    nome.trim(), 
                    sobrenome.trim(), 
                    nacionalidade == null ? "" : nacionalidade.trim()
            );
            
            return autorDAO.atualizar(autor) 
                    ? "SUCESSO" 
                    : "Erro ao atualizar autor.";
        
        } catch (NumberFormatException e) {
            return "ID inválido.";
        }
    }

    public String excluir(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            return autorDAO.excluir(id) ? "SUCESSO" : "Erro ao excluir o autor.";
        } catch (NumberFormatException e) {
            return "ID inválido.";
        }
    }
}