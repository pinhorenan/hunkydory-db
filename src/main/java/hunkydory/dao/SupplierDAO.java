package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.Supplier;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class SupplierDAO extends BaseDAO<Supplier> implements GenericDAO<Supplier> {

    @Override
    public boolean insert(Supplier supplier) {
        String sql = "INSERT INTO fornecedor (id_fornecedor, nome, contato) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, supplier.getSupplierID());
            ps.setString(2, supplier.getName());
            ps.setString(3, supplier.getContact());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Supplier supplier) {
        String sql = "UPDATE fornecedor SET nome = ?, contato = ? WHERE id_fornecedor = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getContact());
            ps.setInt(3, supplier.getSupplierID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM fornecedor WHERE id_fornecedor = ?";
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
    public List<Supplier> listAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT id_fornecedor, nome, contato FROM fornecedor";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Supplier s = new Supplier(
                        rs.getInt("id_fornecedor"),
                        rs.getString("nome"),
                        rs.getString("contato")
                );
                list.add(s);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Supplier searchByID(int id) {
        String sql = "SELECT id_fornecedor, nome, contato FROM fornecedor WHERE id_fornecedor = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Supplier(
                            rs.getInt("id_fornecedor"),
                            rs.getString("nome"),
                            rs.getString("contato")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
