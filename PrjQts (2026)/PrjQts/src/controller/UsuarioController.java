package controller;

import dao.UsuarioDAO;
import model.UsuarioModel;
import java.util.ArrayList;
import java.util.List;

public class UsuarioController {
    private UsuarioDAO usuarioDAO;

    public UsuarioController() {
        this.usuarioDAO = new UsuarioDAO();
    }

    public String cadastrar(String nome, String sobrenome, String email, String telefone, String status) {
        if (nome == null || nome.trim().isEmpty()) {
            return "O nome do usuário é obrigatório.";
        }
        UsuarioModel u = new UsuarioModel(0, nome.trim(), sobrenome.trim(), email.trim(), telefone.trim(), null, status);
        return usuarioDAO.inserir(u) ? "SUCESSO" : "Erro ao cadastrar usuário.";
    }

    public List<Object[]> listarParaTabela() {
        List<UsuarioModel> lista = usuarioDAO.listar();
        List<Object[]> dados = new ArrayList<>();
        for (UsuarioModel u : lista) {
            dados.add(new Object[]{
                u.getId(), u.getNome(), u.getSobrenome(), u.getEmail(), u.getTelefone(), u.getDataCadastro(), u.getStatus()
            });
        }
        return dados;
    }

    public String atualizar(String idStr, String nome, String sobrenome, String email, String telefone, String status) {
        if (idStr == null || idStr.trim().isEmpty()) return "Selecione um usuário.";
        if (nome == null || nome.trim().isEmpty()) return "O nome do usuário é obrigatório.";

        try {
            int id = Integer.parseInt(idStr.trim());
            UsuarioModel u = new UsuarioModel(id, nome.trim(), sobrenome.trim(), email.trim(), telefone.trim(), null, status);
            return usuarioDAO.atualizar(u) ? "SUCESSO" : "Erro ao atualizar usuário.";
        } catch (NumberFormatException e) {
            return "ID inválido.";
        }
    }

    public String excluir(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            return usuarioDAO.excluir(id) ? "SUCESSO" : "Erro ao excluir usuário.";
        } catch (NumberFormatException e) {
            return "ID inválido.";
        }
    }
}