package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.CustomerAddress;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"CallToPrintStackTrace", "unused"})
public class CustomerAddressDAO extends BaseDAO<CustomerAddress> implements GenericDAO<CustomerAddress> {

    @Override
    public boolean insert(CustomerAddress address) {
        String sql = "INSERT INTO endereco_cliente (id_endereco, rua, numero, cidade, estado, cep, complemento, id_cliente) "
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
            ps.setInt(8, address.getCostumerID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(CustomerAddress address) {
        String sql = "UPDATE endereco_cliente "
                + "SET rua = ?, numero = ?, cidade = ?, estado = ?, cep = ?, complemento = ?, id_cliente = ? "
                + "WHERE id_endereco = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, address.getStreet());
            ps.setString(2, address.getNumber());
            ps.setString(3, address.getCity());
            ps.setString(4, address.getState());
            ps.setString(5, address.getZipCode());
            ps.setString(6, address.getComplement());
            ps.setInt(7, address.getCostumerID());
            ps.setInt(8, address.getAddressID());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int addressID) {
        String sql = "DELETE FROM endereco_cliente WHERE id_endereco = ?";
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
    public List<CustomerAddress> listAll() {
        List<CustomerAddress> list = new ArrayList<>();
        String sql = "SELECT id_endereco, rua, numero, cidade, estado, cep, complemento, id_cliente FROM endereco_cliente";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                CustomerAddress ca = new CustomerAddress(
                        rs.getInt("id_endereco"),
                        rs.getString("rua"),
                        rs.getString("numero"),
                        rs.getString("cidade"),
                        rs.getString("estado"),
                        rs.getString("cep"),
                        rs.getString("complemento")
                );
                ca.setCostumerID(rs.getInt("id_cliente"));
                list.add(ca);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public CustomerAddress searchByID(int addressID) {
        String sql = "SELECT id_endereco, rua, numero, cidade, estado, cep, complemento, id_cliente "
                + "FROM endereco_cliente WHERE id_endereco = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, addressID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CustomerAddress ca = new CustomerAddress(
                            rs.getInt("id_endereco"),
                            rs.getString("rua"),
                            rs.getString("numero"),
                            rs.getString("cidade"),
                            rs.getString("estado"),
                            rs.getString("cep"),
                            rs.getString("complemento")
                    );
                    ca.setCostumerID(rs.getInt("id_cliente"));
                    return ca;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
