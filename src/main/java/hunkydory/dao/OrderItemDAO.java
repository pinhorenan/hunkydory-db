package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.OrderItem;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class OrderItemDAO extends BaseDAO<OrderItem> implements GenericDAO<OrderItem> {

    @Override
    public boolean insert(OrderItem orderItem) {
        String sql = "INSERT INTO itempedido (id_pedido, id_produto, quantidade, preco_unitario) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItem.getOrderId());
            ps.setInt(2, orderItem.getProductId());
            ps.setInt(3, orderItem.getQuantity());
            ps.setBigDecimal(4, orderItem.getUnitPrice());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(OrderItem orderItem) {
        String sql = "UPDATE itempedido SET quantidade = ?, preco_unitario = ? WHERE id_pedido = ? AND id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderItem.getQuantity());
            ps.setBigDecimal(2, orderItem.getUnitPrice());
            ps.setInt(3, orderItem.getOrderId());
            ps.setInt(4, orderItem.getProductId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Método genérico não se aplica para chave composta
    @Override
    public boolean delete(int id) {
        throw new UnsupportedOperationException("Use delete(int orderId, int productId) instead.");
    }

    public boolean delete(int orderId, int productId) {
        String sql = "DELETE FROM itempedido WHERE id_pedido = ? AND id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<OrderItem> listAll() {
        List<OrderItem> list = new ArrayList<>();
        String sql = "SELECT id_pedido, id_produto, quantidade, preco_unitario FROM itempedido";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                OrderItem item = new OrderItem(
                        rs.getInt("id_pedido"),
                        rs.getInt("id_produto"),
                        rs.getInt("quantidade"),
                        rs.getBigDecimal("preco_unitario")
                );
                list.add(item);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // Genérico não se aplica para chave composta
    @Override
    public OrderItem searchByID(int id) {
        throw new UnsupportedOperationException("Use searchByID(int orderId, int productId) instead.");
    }

    @SuppressWarnings("unused")
    public OrderItem searchByID(int orderId, int productId) {
        String sql = "SELECT id_pedido, id_produto, quantidade, preco_unitario FROM itempedido WHERE id_pedido = ? AND id_produto = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            ps.setInt(2, productId);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new OrderItem(
                            rs.getInt("id_pedido"),
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
