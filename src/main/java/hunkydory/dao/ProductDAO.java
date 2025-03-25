/*
 * =============================================================================
 *  5) ProductDAO.java
 *  TABLE: produto
 *     - id_produto (PK)
 *     - nome (NOT NULL)
 *     - preco (NUMERIC(10,2), NOT NULL)
 *     - estoque (INT NOT NULL DEFAULT 0)
 *     - descricao (TEXT)
 *     - id_categoria (FK)
 *     - id_fornecedor (FK)
 * =============================================================================
 */
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
        String sql = "INSERT INTO produto (id_produto, nome, preco, estoque, descricao, id_categoria, id_fornecedor) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, product.getProductID());
            ps.setString(2, product.getName());
            ps.setBigDecimal(3, product.getPrice());
            ps.setInt(4, product.getStock());
            ps.setString(5, product.getDescription());
            ps.setInt(6, product.getCategoryID());
            ps.setInt(7, product.getSupplierID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Product product) {
        String sql = "UPDATE produto SET nome = ?, preco = ?, estoque = ?, descricao = ?, "
                + "id_categoria = ?, id_fornecedor = ? WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, product.getName());
            ps.setBigDecimal(2, product.getPrice());
            ps.setInt(3, product.getStock());
            ps.setString(4, product.getDescription());
            ps.setInt(5, product.getCategoryID());
            ps.setInt(6, product.getSupplierID());
            ps.setInt(7, product.getProductID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int productID) {
        String sql = "DELETE FROM produto WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Product> listAll() {
        List<Product> list = new ArrayList<>();
        String sql = "SELECT id_produto, nome, preco, estoque, descricao, id_categoria, id_fornecedor FROM produto";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("id_produto"),
                        rs.getInt("id_categoria"),
                        rs.getInt("id_fornecedor"),
                        rs.getString("nome"),
                        rs.getBigDecimal("preco"),
                        rs.getInt("estoque"),
                        rs.getString("descricao")
                );
                list.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Product searchByID(int productID) {
        String sql = "SELECT id_produto, nome, preco, estoque, descricao, id_categoria, id_fornecedor "
                + "FROM produto WHERE id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, productID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Product(
                            rs.getInt("id_produto"),
                            rs.getInt("id_categoria"),
                            rs.getInt("id_fornecedor"),
                            rs.getString("nome"),
                            rs.getBigDecimal("preco"),
                            rs.getInt("estoque"),
                            rs.getString("descricao")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
