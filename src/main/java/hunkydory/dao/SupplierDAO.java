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
        String sql = "INSERT INTO fornecedor (nome, cnpj, contato) VALUES (?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getCNPJ());
            ps.setString(3, supplier.getContact());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Supplier supplier) {
        String sql = "UPDATE fornecedor SET nome = ?, contato = ? WHERE cnpj = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, supplier.getName());
            ps.setString(2, supplier.getContact());
            ps.setString(3, supplier.getCNPJ());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Aqui, o parâmetro para exclusão é o CNPJ
    @Override
    public boolean delete(int id) {
        // Não usamos ID numérico; se precisar, lance exceção ou converta
        throw new UnsupportedOperationException("Utilize delete(String cnpj) para fornecedores.");
    }

    public boolean delete(String cnpj) {
        String sql = "DELETE FROM fornecedor WHERE cnpj = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cnpj);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Supplier> listAll() {
        List<Supplier> list = new ArrayList<>();
        String sql = "SELECT nome, cnpj, contato FROM fornecedor";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Supplier supplier = new Supplier(
                        rs.getString("cnpj"),
                        rs.getString("nome"),
                        rs.getString("contato")
                );
                list.add(supplier);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // O método searchByID será adaptado para searchByCNPJ
    @Override
    public Supplier searchByID(int id) {
        throw new UnsupportedOperationException("Use searchByCNPJ(String cnpj) instead.");
    }

    public Supplier searchByCNPJ(String cnpj) {
        String sql = "SELECT nome, cnpj, contato FROM fornecedor WHERE cnpj = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cnpj);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Supplier(
                            rs.getString("cnpj"),
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
