package hunkydory.dao;

import hunkydory.dao.base.BaseDAO;
import hunkydory.dao.base.GenericDAO;
import hunkydory.model.Review;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"CallToPrintStackTrace", "unused"})
public class ReviewDAO extends BaseDAO<Review> implements GenericDAO<Review> {

    @Override
    public boolean insert(Review review) {
        String sql = "INSERT INTO avaliacao (id_avaliacao, nota, comentario, data, id_cliente, id_compra) "
                + "VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, review.getReviewID());
            ps.setInt(2, review.getRating());
            ps.setString(3, review.getComment());
            ps.setDate(4, review.getDate() != null ? Date.valueOf(review.getDate()) : null);
            ps.setInt(5, review.getCustomerID());
            ps.setInt(6, review.getOrderID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean update(Review review) {
        String sql = "UPDATE avaliacao SET nota = ?, comentario = ?, data = ?, id_cliente = ?, id_compra = ? "
                + "WHERE id_avaliacao = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, review.getRating());
            ps.setString(2, review.getComment());
            ps.setDate(3, review.getDate() != null ? Date.valueOf(review.getDate()) : null);
            ps.setInt(4, review.getCustomerID());
            ps.setInt(5, review.getOrderID());
            ps.setInt(6, review.getReviewID());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean delete(int reviewID) {
        String sql = "DELETE FROM avaliacao WHERE id_avaliacao = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reviewID);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Review> listAll() {
        List<Review> list = new ArrayList<>();
        String sql = "SELECT id_avaliacao, nota, comentario, data, id_cliente, id_compra FROM avaliacao";
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Date d = rs.getDate("data");
                LocalDate dateObj = (d != null) ? d.toLocalDate() : null;
                Review r = new Review(
                        rs.getInt("id_avaliacao"),
                        rs.getInt("id_cliente"),
                        rs.getInt("id_compra"),
                        rs.getInt("nota"),
                        rs.getString("comentario"),
                        dateObj
                );
                list.add(r);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public Review searchByID(int reviewID) {
        String sql = "SELECT id_avaliacao, nota, comentario, data, id_cliente, id_compra FROM avaliacao WHERE id_avaliacao = ?";
        try (Connection conn = getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, reviewID);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date d = rs.getDate("data");
                    LocalDate dateObj = (d != null) ? d.toLocalDate() : null;
                    return new Review(
                            rs.getInt("id_avaliacao"),
                            rs.getInt("id_cliente"),
                            rs.getInt("id_compra"),
                            rs.getInt("nota"),
                            rs.getString("comentario"),
                            dateObj
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
