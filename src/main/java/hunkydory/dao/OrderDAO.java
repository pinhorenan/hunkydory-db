package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("CallToPrintStackTrace")
public class OrderDAO extends BaseDAO<Order> implements GenericDAO<Order> {

    @Override
    public boolean insert(Order order) {
        String sql = "INSERT INTO pedido (id_pedido, data, status, id_cliente) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, order.getIdOrder());
            ps.setDate(2, Date.valueOf(order.getOrderDate()));
            ps.setString(3, order.getStatus());
            ps.setInt(4, order.getCustomerId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Order order) {
        String sql = "UPDATE pedido SET data = ?, status = ?, id_cliente = ? WHERE id_pedido = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setDate(1, Date.valueOf(order.getOrderDate()));
            ps.setString(2, order.getStatus());
            ps.setInt(3, order.getCustomerId());
            ps.setInt(4, order.getIdOrder());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
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
    public List<Order> listAll() {
        List<Order> list = new ArrayList<>();
        String sql = "SELECT id_pedido, data, status, id_cliente FROM pedido";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("id_pedido"),
                        rs.getDate("data").toLocalDate(),
                        rs.getString("status"),
                        rs.getInt("id_cliente")
                );
                list.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Order searchByID(int id) {
        String sql = "SELECT id_pedido, data, status, id_cliente FROM pedido WHERE id_pedido = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try(ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Order(
                            rs.getInt("id_pedido"),
                            rs.getDate("data").toLocalDate(),
                            rs.getString("status"),
                            rs.getInt("id_cliente")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
