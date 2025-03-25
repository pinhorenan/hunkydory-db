package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.DeliveryAddress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"CallToPrintStackTrace", "unused"})
public class DeliveryAddressDAO extends BaseDAO<DeliveryAddress> implements GenericDAO<DeliveryAddress> {

    @Override
    public boolean insert(DeliveryAddress address) {
        String sql = "INSERT INTO endereco_entrega (id_endereco, rua, numero, cidade, estado, cep, complemento, id_compra) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, address.getAddressID());
            ps.setString(2, address.getStreet());
            ps.setString(3, address.getNumber());
            ps.setString(4, address.getCity());
            ps.setString(5, address.getState());
            ps.setString(6, address.getZipCode());
            ps.setString(7, address.getComplement());
            ps.setInt(8, address.getOrderID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(DeliveryAddress addr) {
        String sql = "UPDATE endereco_entrega "
                + "SET rua = ?, numero = ?, cidade = ?, estado = ?, cep = ?, complemento = ?, id_compra = ? "
                + "WHERE id_endereco = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, addr.getStreet());
            ps.setString(2, addr.getNumber());
            ps.setString(3, addr.getCity());
            ps.setString(4, addr.getState());
            ps.setString(5, addr.getZipCode());
            ps.setString(6, addr.getComplement());
            ps.setInt(7, addr.getOrderID());
            ps.setInt(8, addr.getAddressID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int addressID) {
        String sql = "DELETE FROM endereco_entrega WHERE id_endereco = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, addressID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<DeliveryAddress> listAll() {
        List<DeliveryAddress> list = new ArrayList<>();
        String sql = "SELECT id_endereco, rua, numero, cidade, estado, cep, complemento, id_compra FROM endereco_entrega";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                DeliveryAddress da = new DeliveryAddress(
                        rs.getInt("id_endereco"),
                        rs.getInt("id_compra"),
                        rs.getString("rua"),
                        rs.getString("numero"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep"),
                        rs.getString("complemento")
                );
                list.add(da);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public DeliveryAddress searchByID(int addressID) {
        String sql = "SELECT id_endereco, rua, numero, cidade, estado, cep, complemento, id_compra "
                + "FROM endereco_entrega WHERE id_endereco = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, addressID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new DeliveryAddress(
                            rs.getInt("id_endereco"),
                            rs.getInt("id_compra"),
                            rs.getString("rua"),
                            rs.getString("numero"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("cep"),
                            rs.getString("complemento")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
