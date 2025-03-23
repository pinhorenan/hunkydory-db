package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.Delivery;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"CallToPrintStackTrace", "unused"})
public class DeliveryDAO extends BaseDAO<Delivery> implements GenericDAO<Delivery> {

    @Override
    public boolean insert(Delivery delivery) {
        String sql = "INSERT INTO entrega (id_entrega, id_pedido, data_entrega, status) VALUES (?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, delivery.getIdDelivery());
            ps.setInt(2, delivery.getOrderId());
            ps.setDate(3, Date.valueOf(delivery.getDeliveryDate()));
            ps.setString(4, delivery.getStatus());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Delivery delivery) {
        String sql = "UPDATE entrega SET id_pedido = ?, data_entrega = ?, status = ? WHERE id_entrega = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, delivery.getOrderId());
            ps.setDate(2, Date.valueOf(delivery.getDeliveryDate()));
            ps.setString(3, delivery.getStatus());
            ps.setInt(4, delivery.getIdDelivery());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM entrega WHERE id_entrega = ?";
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
    public List<Delivery> listAll() {
        List<Delivery> list = new ArrayList<>();
        String sql = "SELECT id_entrega, id_pedido, data_entrega, status FROM entrega";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Delivery d = new Delivery(
                        rs.getInt("id_entrega"),
                        rs.getInt("id_pedido"),
                        rs.getDate("data_entrega").toLocalDate(),
                        rs.getString("status")
                );
                list.add(d);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Delivery searchByID(int id) {
        String sql = "SELECT id_entrega, id_pedido, data_entrega, status FROM entrega WHERE id_entrega = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()){
                if(rs.next()){
                    return new Delivery(
                            rs.getInt("id_entrega"),
                            rs.getInt("id_pedido"),
                            rs.getDate("data_entrega").toLocalDate(),
                            rs.getString("status")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
