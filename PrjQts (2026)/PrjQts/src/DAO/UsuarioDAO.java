package dao;

import model.UsuarioModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    public boolean inserir(UsuarioModel usuario) {
        String sql = "INSERT INTO Usuario (nomeUsuario, sobrenomeUsuario, emailUsuario, telefoneUsuario, statusUsuario) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSobrenome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getStatus());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<UsuarioModel> listar() {
        List<UsuarioModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Usuario";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                UsuarioModel u = new UsuarioModel();
                u.setId(rs.getInt("idUsuario"));
                u.setNome(rs.getString("nomeUsuario"));
                u.setSobrenome(rs.getString("sobrenomeUsuario"));
                u.setEmail(rs.getString("emailUsuario"));
                u.setTelefone(rs.getString("telefoneUsuario"));
                u.setDataCadastro(rs.getDate("dataCadastro"));
                u.setStatus(rs.getString("statusUsuario"));
                lista.add(u);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<UsuarioModel> consultar(String nome, String sobrenome, String email, String telefone, String status) {
    List<UsuarioModel> lista = new ArrayList<>();

    String sql;
    String valor;

    if (!nome.trim().isEmpty()) {

        sql = "SELECT * FROM Usuario WHERE nomeUsuario LIKE ?";
        valor = "%" + nome.trim() + "%";

    } else if (!sobrenome.trim().isEmpty()) {

        sql = "SELECT * FROM Usuario WHERE sobrenomeUsuario LIKE ?";
        valor = "%" + sobrenome.trim() + "%";

    } else if (!email.trim().isEmpty()) {

        sql = "SELECT * FROM Usuario WHERE emailUsuario LIKE ?";
        valor = "%" + email.trim() + "%";

    } else if (!telefone.trim().isEmpty()) {

        sql = "SELECT * FROM Usuario WHERE telefoneUsuario LIKE ?";
        valor = "%" + telefone.trim() + "%";

    } else if (!status.trim().isEmpty()) {

        sql = "SELECT * FROM Usuario WHERE statusUsuario = ?";
        valor = status.trim();

    } else {

        sql = "SELECT * FROM Usuario";
        valor = null;
    }

    try (Connection conn = Conexao.getConexao();
         PreparedStatement stmt = conn.prepareStatement(sql)) {

        if (valor != null) {
            stmt.setString(1, valor);
        }

        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            UsuarioModel usuario = new UsuarioModel();

            usuario.setId(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nomeUsuario"));
            usuario.setSobrenome(rs.getString("sobrenomeUsuario"));
            usuario.setEmail(rs.getString("emailUsuario"));
            usuario.setTelefone(rs.getString("telefoneUsuario"));
            usuario.setStatus(rs.getString("statusUsuario"));

            lista.add(usuario);
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }

    return lista;
}

    public boolean atualizar(UsuarioModel usuario) {
        String sql = "UPDATE Usuario SET nomeUsuario = ?, sobrenomeUsuario = ?, emailUsuario = ?, telefoneUsuario = ?, statusUsuario = ? WHERE idUsuario = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, usuario.getNome());
            stmt.setString(2, usuario.getSobrenome());
            stmt.setString(3, usuario.getEmail());
            stmt.setString(4, usuario.getTelefone());
            stmt.setString(5, usuario.getStatus());
            stmt.setInt(6, usuario.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Usuario WHERE idUsuario = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}