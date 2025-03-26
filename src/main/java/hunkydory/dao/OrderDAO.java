package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.math.BigDecimal;

@SuppressWarnings("CallToPrintStackTrace")
public class OrderDAO extends BaseDAO<Order> implements GenericDAO<Order> {

    @Override
    public boolean insert(Order order) {
        String sql = "INSERT INTO compra (id_compra, status, valor_total, data_pedido, data_entrega, id_cliente) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, order.getOrderID());
            ps.setString(2, order.getStatus());
            ps.setBigDecimal(3, order.getTotalValue() == null ? BigDecimal.ZERO : order.getTotalValue());
            ps.setDate(4, order.getOrderDate() != null ? Date.valueOf(order.getOrderDate()) : null);
            ps.setDate(5, order.getDeliveryDate() != null ? Date.valueOf(order.getDeliveryDate()) : null);
            ps.setInt(6, order.getCustomerID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE compra SET status = ?, valor_total = ?, data_pedido = ?, data_entrega = ?, id_cliente = ? "
                + "WHERE id_compra = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, order.getStatus());
            ps.setBigDecimal(2, order.getTotalValue());
            ps.setDate(3, order.getOrderDate() != null ? Date.valueOf(order.getOrderDate()) : null);
            ps.setDate(4, order.getDeliveryDate() != null ? Date.valueOf(order.getDeliveryDate()) : null);
            ps.setInt(5, order.getCustomerID());
            ps.setInt(6, order.getOrderID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int orderID) {
        String sql = "DELETE FROM compra WHERE id_compra = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Order> listAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT id_compra, status, valor_total, data_pedido, data_entrega, id_cliente FROM compra";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order o = new Order(
                        rs.getInt("id_compra"),
                        rs.getInt("id_cliente"),
                        rs.getString("status"),
                        rs.getBigDecimal("valor_total"),
                        rs.getDate("data_pedido") != null ? rs.getDate("data_pedido").toLocalDate() : null,
                        rs.getDate("data_entrega") != null ? rs.getDate("data_entrega").toLocalDate() : null
                );
                list.add(o);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Order searchByID(int orderID) {
        String sql = "SELECT id_compra, status, valor_total, data_pedido, data_entrega, id_cliente "
                + "FROM compra WHERE id_compra = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, orderID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Order(
                            rs.getInt("id_compra"),
                            rs.getInt("id_cliente"),
                            rs.getString("status"),
                            rs.getBigDecimal("valor_total"),
                            rs.getDate("data_pedido") != null ? rs.getDate("data_pedido").toLocalDate() : null,
                            rs.getDate("data_entrega") != null ? rs.getDate("data_entrega").toLocalDate() : null
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
