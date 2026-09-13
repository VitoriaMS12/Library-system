package dao;

import model.CategoriaModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoriaDAO {

    public boolean inserir(CategoriaModel cat) {
        String sql = "INSERT INTO Categoria (nome_Categoria, Descricao_categoria) VALUES (?, ?)";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cat.getNome());
            stmt.setString(2, cat.getDescricao());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<CategoriaModel> listar() {
        List<CategoriaModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM Categoria";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                CategoriaModel c = new CategoriaModel();
                c.setId(rs.getInt("idCategoria"));
                c.setNome(rs.getString("nome_Categoria"));
                c.setDescricao(rs.getString("Descricao_categoria"));
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean atualizar(CategoriaModel cat) {
        String sql = "UPDATE Categoria SET nome_Categoria = ?, Descricao_categoria = ? WHERE idCategoria = ?";
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, cat.getNome());
            stmt.setString(2, cat.getDescricao());
            stmt.setInt(3, cat.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Categoria WHERE idCategoria = ?";
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