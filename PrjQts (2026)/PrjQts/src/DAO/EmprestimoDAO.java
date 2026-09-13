package dao;

import model.EmprestimoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAO {

    public boolean registrarEmprestimo(EmprestimoModel emp) {
        String sqlEmprestimo = "INSERT INTO Emprestimo (idLivro, idUsuario, dataEmprestimo, dataDevolucaoPrevista, statusEmprestimo) VALUES (?, ?, ?, ?, 'Ativo')";
        String sqlAtualizaLivro = "UPDATE Livro SET Quantidade_disponivel = Quantidade_disponivel - 1 WHERE idLivro = ? AND Quantidade_disponivel > 0";

        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false); // Inicia transação

            // 1. Desconta estoque do livro
            try (PreparedStatement stmtLivro = conn.prepareStatement(sqlAtualizaLivro)) {
                stmtLivro.setInt(1, emp.getIdLivro());
                int linhasAfetadas = stmtLivro.executeUpdate();
                if (linhasAfetadas == 0) {
                    conn.rollback(); // Livro sem estoque
                    return false;
                }
            }

            // 2. Insere o registro de empréstimo
            try (PreparedStatement stmtEmp = conn.prepareStatement(sqlEmprestimo)) {
                stmtEmp.setInt(1, emp.getIdLivro());
                stmtEmp.setInt(2, emp.getIdUsuario());
                stmtEmp.setDate(3, emp.getDataEmprestimo());
                stmtEmp.setDate(4, emp.getDataDevolucaoPrevista());
                stmtEmp.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao(conn);
        }
    }

    public boolean registrarDevolucao(int idEmprestimo, int idLivro, java.sql.Date dataDevolucao) {
        String sqlDevolucao = "UPDATE Emprestimo SET dataDevolucaoReal = ?, statusEmprestimo = 'Devolvido' WHERE idEmprestimo = ? AND statusEmprestimo = 'Ativo'";
        String sqlAtualizaLivro = "UPDATE Livro SET Quantidade_disponivel = Quantidade_disponivel + 1 WHERE idLivro = ?";

        Connection conn = null;
        try {
            conn = Conexao.getConexao();
            conn.setAutoCommit(false);

            // 1. Atualiza status do empréstimo
            try (PreparedStatement stmtEmp = conn.prepareStatement(sqlDevolucao)) {
                stmtEmp.setDate(1, dataDevolucao);
                stmtEmp.setInt(2, idEmprestimo);
                int linhas = stmtEmp.executeUpdate();
                if (linhas == 0) {
                    conn.rollback();
                    return false;
                }
            }

            // 2. Devolve +1 quantidade ao livro
            try (PreparedStatement stmtLivro = conn.prepareStatement(sqlAtualizaLivro)) {
                stmtLivro.setInt(1, idLivro);
                stmtLivro.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            e.printStackTrace();
            return false;
        } finally {
            Conexao.fecharConexao(conn);
        }
    }

    public List<EmprestimoModel> listar() {
        List<EmprestimoModel> lista = new ArrayList<>();
        String sql = "SELECT e.*, l.Titulo_livro, u.nomeUsuario FROM Emprestimo e " +
                     "INNER JOIN Livro l ON e.idLivro = l.idLivro " +
                     "INNER JOIN Usuario u ON e.idUsuario = u.idUsuario";

        try (Connection conn = Conexao.getConexao();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                EmprestimoModel emp = new EmprestimoModel();
                emp.setId(rs.getInt("idEmprestimo"));
                emp.setIdLivro(rs.getInt("idLivro"));
                emp.setIdUsuario(rs.getInt("idUsuario"));
                emp.setDataEmprestimo(rs.getDate("dataEmprestimo"));
                emp.setDataDevolucaoPrevista(rs.getDate("dataDevolucaoPrevista"));
                emp.setDataDevolucaoReal(rs.getDate("dataDevolucaoReal"));
                emp.setStatus(rs.getString("statusEmprestimo"));
                emp.setTituloLivro(rs.getString("Titulo_livro"));
                emp.setNomeUsuario(rs.getString("nomeUsuario"));
                lista.add(emp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }
}