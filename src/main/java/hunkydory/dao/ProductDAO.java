package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class ProductDAO extends BaseDAO<Product> implements GenericDAO<Product> {

    @Override
    public boolean insert(Product product) {
        String sql = "INSERT INTO produto (id_produto, nome, preco, descricao, categoria, cnpj_fornecedor) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, product.getIdProduct());
            ps.setString(2, product.getName());
            ps.setBigDecimal(3, product.getPrice());
            ps.setString(4, product.getDescription());
            ps.setString(5, product.getCategory());
            ps.setString(6, product.getSupplierCNPJ());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, descricao = ?, categoria = ?, cnpj_fornecedor = ? WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setString(3, product.getDescription());
            ps.setString(4, product.getCategory());
            ps.setString(5, product.getSupplierCNPJ());
            ps.setInt(6, product.getIdProduct());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
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
    public List<Product> listAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id_produto, nome, preco, descricao, categoria, cnpj_fornecedor FROM produto";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("id_produto"),
                        rs.getString("nome"),
                        rs.getBigDecimal("preco"),
                        rs.getString("descricao"),
                        rs.getString("categoria"),
                        rs.getString("cnpj_fornecedor")
                );
                list.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product searchByID(int id) {
        String sql = "SELECT id_produto, nome, preco, descricao, categoria, cnpj_fornecedor FROM produto WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Product(
                            rs.getInt("id_produto"),
                            rs.getString("nome"),
                            rs.getBigDecimal("preco"),
                            rs.getString("descricao"),
                            rs.getString("categoria"),
                            rs.getString("cnpj_fornecedor")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
