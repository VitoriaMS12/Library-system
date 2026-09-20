package controller;

import dao.FuncionarioDAO;
import model.FuncionarioModel;

public class FuncionarioController {
    
    private FuncionarioDAO funcionarioDAO;
    
    public FuncionarioController() {
        funcionarioDAO = new FuncionarioDAO();
    }
    
    public boolean cadastrar(FuncionarioModel funcionario) {
        
        if (funcionario.getNome() == null || funcionario.getNome().trim().isEmpty()) {
            return false;
        }
        
        if (funcionario.getSobrenome() == null
                || funcionario.getSobrenome().trim().isEmpty()) {
            return false;
        }

        if (funcionario.getCodigo() == null
                || funcionario.getCodigo().trim().isEmpty()) {
            return false;
        }

        if (funcionario.getSenha() == null
                || funcionario.getSenha().trim().isEmpty()) {
            return false;
        }
        
        return funcionarioDAO.inserir(funcionario);
    }
    
    public FuncionarioModel buscarPorCodigo(String codigo) {
        
        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        return funcionarioDAO.buscarPorCodigo(codigo.trim());
    }

    public boolean validarLogin(String codigo, String senha) {

        if (codigo == null || codigo.trim().isEmpty()) {
            return false;
        }

        if (senha == null || senha.isEmpty()) {
            return false;
        }

        FuncionarioModel funcionario =
                funcionarioDAO.buscarPorCodigo(codigo.trim());

        if (funcionario == null) {
            return false;
        }

        return funcionarioDAO.verificarSenha(
                senha,
                funcionario.getSenha()
        );
    }
    
}
