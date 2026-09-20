package dao;

import model.FuncionarioModel;
//import dao.Conexao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

public class FuncionarioDAO {
    
    public boolean inserir(FuncionarioModel funcionario) {
        
        String sql = "INSERT INTO Funcionario"
                + "(nomeFuncionario, sobrenomeFuncionario,"
                + "codigoFuncionario, senhaFuncionario)"
                + "VALUES (?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            String senhaHash = BCrypt.hashpw(
                    funcionario.getSenha(),
                    BCrypt.gensalt(10)
            );
            
            stmt.setString(1, funcionario.getNome());
            stmt.setString(2, funcionario.getSobrenome());
            stmt.setString(3, funcionario.getCodigo());
            stmt.setString(4, senhaHash);
            
            stmt.executeUpdate();
            
            return true;
            
        }catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
    public FuncionarioModel buscarPorCodigo(String codigo) {
        
        String sql = "SELECT * FROM Funcionario "
                + "WHERE codigoFuncionario = ?";
        
        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, codigo);
            
            try (ResultSet rs = stmt.executeQuery()) {
                
                if (rs.next()) {
                    
                    FuncionarioModel funcionario = new FuncionarioModel();
                    
                    funcionario.setId(
                    rs.getInt("idFuncionario")
                    );
                    
                    funcionario.setNome(
                    rs.getString("nomeFuncionario")
                    );
                    
                    funcionario.setSobrenome(
                    rs.getString("sobrenomeFuncionario")
                    );
                    
                    funcionario.setCodigo(
                    rs.getString("codigoFuncionario")
                    );
                    
                    funcionario.setSenha(
                    rs.getString("senhaFuncionario")
                    );
                    
                    return funcionario;
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }
    
    public boolean verificarSenha(String senhaDigitada, String senhaHash) {
        
        return BCrypt.checkpw(senhaDigitada, senhaHash);
    }
    
}
