package dao;

import model.AutorModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AutorDAO {

    public boolean inserir(AutorModel autor) {
        String sql = "INSERT INTO Autor (Nome_autor, Sobrenome_autor, Nacionalidade_autor) VALUES (?, ?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getSobrenome());
            stmt.setString(3, autor.getNacionalidade());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<AutorModel> listar() {
        List<AutorModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Autor";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                AutorModel a = new AutorModel();
                a.setId(rs.getInt("idAutor"));
                a.setNome(rs.getString("Nome_autor"));
                a.setSobrenome(rs.getString("Sobrenome_autor"));
                a.setNacionalidade(rs.getString("Nacionalidade_autor"));
                lista.add(a);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(AutorModel autor) {
        String sql = "UPDATE Autor SET Nome_autor = ?, Sobrenome_autor = ?, Nacionalidade_autor = ? WHERE idAutor = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, autor.getNome());
            stmt.setString(2, autor.getSobrenome());
            stmt.setString(3, autor.getNacionalidade());
            stmt.setInt(4, autor.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Autor WHERE idAutor = ?";
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