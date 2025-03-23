package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.Inventory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class InventoryDAO extends BaseDAO<Inventory> implements GenericDAO<Inventory> {

    @Override
    public boolean insert(Inventory inventory) {
        String sql = "INSERT INTO estoque (id_produto, quantidade_disponivel) VALUES (?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inventory.getProductId());
            ps.setInt(2, inventory.getQuantityAvailable());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Inventory inventory) {
        String sql = "UPDATE estoque SET quantidade_disponivel = ? WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, inventory.getQuantityAvailable());
            ps.setInt(2, inventory.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM estoque WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Inventory> listAll() {
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT id_produto, quantidade_disponivel FROM estoque";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Inventory inv = new Inventory(
                        rs.getInt("id_produto"),
                        rs.getInt("quantidade_disponivel")
                );
                list.add(inv);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Específico para a tela de estoque, para exibir o nome do produto
    public ObservableList<ObservableList<String>> listAllJoin() {
        ObservableList<ObservableList<String>> data = FXCollections.observableArrayList();
        // Consulta que faz JOIN entre estoque e produto para obter o nome do produto
        String sql = "SELECT i.id_produto, p.nome AS productName, i.quantidade_disponivel " +
                "FROM estoque i " +
                "JOIN produto p ON i.id_produto = p.id_produto";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            ResultSetMetaData rsmd = rs.getMetaData();
            int columns = rsmd.getColumnCount();
            while (rs.next()) {
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= columns; i++) {
                    row.add(rs.getString(i));
                }
                data.add(row);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return data;
    }


    @Override
    public Inventory searchByID(int id) {
        String sql = "SELECT id_produto, quantidade_disponivel FROM estoque WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Inventory(
                            rs.getInt("id_produto"),
                            rs.getInt("quantidade_disponivel")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
