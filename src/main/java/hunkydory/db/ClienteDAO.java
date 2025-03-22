package hunkydory.db;

import hunkydory.model.Cliente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe de acesso aos dados da tabela Cliente.
 */
public class ClienteDAO {

    /**
     * Lista todos os clientes do banco.
     */
    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT id_cliente, nome, email, telefone, endereco FROM Cliente";

        try (Connection conn = ConnectionFactory.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int id = rs.getInt("id_cliente");
                String nome = rs.getString("nome");
                String email = rs.getString("email");
                String telefone = rs.getString("telefone");
                String endereco = rs.getString("endereco");

                Cliente c = new Cliente(id, nome, email, telefone, endereco);
                lista.add(c);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    /**
     * Insere um novo cliente no banco.
     */
    public boolean inserir(Cliente cliente) {
        String sql = "INSERT INTO Cliente (id_cliente, nome, email, telefone, endereco) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, cliente.getIdCliente());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getTelefone());
            ps.setString(5, cliente.getEndereco());

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Você pode adicionar outros métodos, como atualizar, deletar, etc.
}