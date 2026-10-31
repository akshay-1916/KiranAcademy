package dao;

import model.Offer;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class OfferDAO {

    public int add(Offer o) throws SQLException {
        String sql = "INSERT INTO offers(application_id, designation, package_lpa, offer_date, joining_date, status) VALUES(?,?,?,?,?,'OFFERED')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, o.getApplicationId());
            ps.setString(2, o.getDesignation());
            ps.setDouble(3, o.getPkg());
            ps.setDate(4, Date.valueOf(o.getOfferDate()));
            ps.setDate(5, Date.valueOf(o.getJoiningDate()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int updateStatus(int id, String status) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE offers SET status=? WHERE offer_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    public Offer findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM offers WHERE offer_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public Offer findByApplication(int applicationId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM offers WHERE application_id=?")) {
            ps.setInt(1, applicationId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Offer> findAll() throws SQLException {
        List<Offer> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM offers ORDER BY offer_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    private Offer map(ResultSet rs) throws SQLException {
        return new Offer(rs.getInt("offer_id"), rs.getInt("application_id"), rs.getString("designation"),
                rs.getDouble("package_lpa"), rs.getDate("offer_date").toLocalDate(),
                rs.getDate("joining_date").toLocalDate(), rs.getString("status"));
    }
}
