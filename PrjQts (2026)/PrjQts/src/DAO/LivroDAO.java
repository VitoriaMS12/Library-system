package dao;

import model.LivroModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class LivroDAO {

    public boolean inserir(LivroModel livro) {
        String sql = "INSERT INTO livro (Titulo_livro, Isbn, Ano_publicacao, Editora, Quantidade, Quantidade_disponivel) VALUES (?, ?, ?, ?, ?, ?)";
        
        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getIsbn());
            stmt.setString(3, livro.getAnoPublicacao());
            stmt.setString(4, livro.getEditora());
            stmt.setInt(5, livro.getQuantidade());
            stmt.setInt(6, livro.getQuantidadeDisponivel());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<LivroModel> listar() {
        List<LivroModel> lista = new ArrayList<>();
        String sql = "SELECT * FROM livro";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                LivroModel livro = new LivroModel();
                livro.setId(rs.getInt("idLivro"));
                livro.setTitulo(rs.getString("Titulo_livro"));
                livro.setIsbn(rs.getString("Isbn"));
                livro.setAnoPublicacao(rs.getString("Ano_publicacao"));
                livro.setEditora(rs.getString("Editora"));
                livro.setQuantidade(rs.getInt("Quantidade"));
                livro.setQuantidadeDisponivel(rs.getInt("Quantidade_disponivel"));
                
                lista.add(livro);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
    
    public List<LivroModel> consultar(String titulo, String isbn, String anoPub, String editora) {
        List<LivroModel> lista = new ArrayList<>();

        String sql;
        String valor;

        if (!titulo.trim().isEmpty()) {

          sql = "SELECT * FROM livro WHERE Titulo_livro LIKE ?";
          valor = "%" + titulo.trim() + "%";

        } else if (!isbn.trim().isEmpty()) {

          sql = "SELECT * FROM livro WHERE Isbn LIKE ?";
          valor = "%" + isbn.trim() + "%";

        } else if (!anoPub.trim().isEmpty()) {

          sql = "SELECT * FROM livro WHERE Ano_publicacao = ?";
          valor = anoPub.trim();

        } else if (!editora.trim().isEmpty()) {

          sql = "SELECT * FROM livro WHERE Editora LIKE ?";
          valor = "%" + editora.trim() + "%";

        } else {

          sql = "SELECT * FROM livro";
          valor = null;
        }

        try (Connection conn = Conexao.getConexao();
           PreparedStatement stmt = conn.prepareStatement(sql)) {

            if (valor != null) {
             stmt.setString(1, valor);
            }

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                LivroModel livro = new LivroModel();

                livro.setId(rs.getInt("idLivro"));
                livro.setTitulo(rs.getString("Titulo_livro"));
                livro.setIsbn(rs.getString("Isbn"));
                livro.setAnoPublicacao(rs.getString("Ano_publicacao"));
                livro.setEditora(rs.getString("Editora"));
                livro.setQuantidade(rs.getInt("Quantidade"));
                livro.setQuantidadeDisponivel(
                  rs.getInt("Quantidade_disponivel")
                );

              lista.add(livro);
            }

        } catch (SQLException e) {
        e.printStackTrace();
        }

      return lista;
    }
    
    public LivroModel buscarPorId(int id) {
        String sql = "SELECT * FROM Livro WHERE idLivro = ?";
        
        try (Connection conn = Conexao.getConexao();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    LivroModel livro = new LivroModel();
                    
                    livro.setId(rs.getInt("idLivro"));
                    livro.setTitulo(rs.getString("Titulo_livro"));
                    livro.setIsbn(rs.getString("Isbn"));
                    livro.setAnoPublicacao(rs.getString("Ano_publicacao"));
                    livro.setEditora(rs.getString("Editora"));
                    livro.setQuantidade(rs.getInt("Quantidade"));
                    livro.setQuantidadeDisponivel(rs.getInt("Quantidade_disponivel"));
                    
                    return livro;
                    
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        
        return null;
    }

    public boolean atualizar(LivroModel livro) {
        String sql = "UPDATE livro SET Titulo_livro = ?, Isbn = ?, Ano_publicacao = ?, Editora = ?, Quantidade = ?, Quantidade_disponivel = ? WHERE idLivro = ?";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, livro.getTitulo());
            stmt.setString(2, livro.getIsbn());
            stmt.setString(3, livro.getAnoPublicacao());
            stmt.setString(4, livro.getEditora());
            stmt.setInt(5, livro.getQuantidade());
            stmt.setInt(6, livro.getQuantidadeDisponivel());
            stmt.setInt(7, livro.getId());

            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM livro WHERE idLivro = ?";

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