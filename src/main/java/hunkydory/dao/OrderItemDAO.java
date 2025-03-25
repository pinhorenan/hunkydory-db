package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.OrderItem;

import java.sql.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class OrderItemDAO extends BaseDAO<OrderItem> implements GenericDAO<OrderItem> {

    @Override
    public boolean insert(OrderItem item) {
        String sql = "INSERT INTO item_compra (id_compra, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getOrderID());
            ps.setInt(2, item.getProductID());
            ps.setInt(3, item.getQuantity());
            ps.setBigDecimal(4, item.getUnitPrice());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OrderItem item) {
        String sql = "UPDATE item_compra SET quantidade = ?, preco_unitario = ? "
                + "WHERE id_compra = ? AND id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, item.getQuantity());
            ps.setBigDecimal(2, item.getUnitPrice());
            ps.setInt(3, item.getOrderID());
            ps.setInt(4, item.getProductID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // The base delete signature won't work for a composite key, so we override:
    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Use delete(int orderID, int productID) instead.");
    }

    public boolean delete(int orderID, int productID) {
        String sql = "DELETE FROM item_compra WHERE id_compra = ? AND id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ps.setInt(2, productID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OrderItem> listAll() {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT id_compra, id_produto, quantidade, preco_unitario FROM item_compra";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                OrderItem oi = new OrderItem(
                        rs.getInt("id_compra"),
                        rs.getInt("id_produto"),
                        rs.getInt("quantidade"),
                        rs.getBigDecimal("preco_unitario")
                );
                list.add(oi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // The base searchByID also won't work for composite key:
    @Override
    public OrderItem searchByID(int id) {
        throw new UnsupportedOperationException("Use searchByID(int orderID, int productID) instead.");
    }

    public OrderItem searchByID(int orderID, int productID) {
        String sql = "SELECT id_compra, id_produto, quantidade, preco_unitario "
                + "FROM item_compra WHERE id_compra = ? AND id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, orderID);
            ps.setInt(2, productID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new OrderItem(
                            rs.getInt("id_compra"),
                            rs.getInt("id_produto"),
                            rs.getInt("quantidade"),
                            rs.getBigDecimal("preco_unitario")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
