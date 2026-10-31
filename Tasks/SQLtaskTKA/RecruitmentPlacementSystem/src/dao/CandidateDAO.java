package dao;

import model.Candidate;
import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class CandidateDAO {

    public int add(Candidate c) throws SQLException {
        String sql = "INSERT INTO candidates(name, email, mobile, technology, percentage, passing_year, backlogs, active) VALUES(?,?,?,?,?,?,?,1)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getMobile());
            ps.setString(4, c.getTechnology());
            ps.setDouble(5, c.getPercentage());
            ps.setInt(6, c.getPassingYear());
            ps.setInt(7, c.getBacklogs());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int update(Candidate c) throws SQLException {
        String sql = "UPDATE candidates SET name=?, email=?, mobile=?, technology=?, percentage=?, passing_year=?, backlogs=? WHERE candidate_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getName());
            ps.setString(2, c.getEmail());
            ps.setString(3, c.getMobile());
            ps.setString(4, c.getTechnology());
            ps.setDouble(5, c.getPercentage());
            ps.setInt(6, c.getPassingYear());
            ps.setInt(7, c.getBacklogs());
            ps.setInt(8, c.getId());
            return ps.executeUpdate();
        }
    }

    public int deactivate(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE candidates SET active=0 WHERE candidate_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public Candidate findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM candidates WHERE candidate_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Candidate> findAll() throws SQLException {
        List<Candidate> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM candidates ORDER BY candidate_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<Candidate> search(String keyword) throws SQLException {
        List<Candidate> list = new ArrayList<>();
        String sql = "SELECT * FROM candidates WHERE name LIKE ? OR technology LIKE ? OR email LIKE ? ORDER BY candidate_id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String like = "%" + keyword + "%";
            ps.setString(1, like);
            ps.setString(2, like);
            ps.setString(3, like);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    public boolean emailExists(String email, int excludeId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM candidates WHERE email=? AND candidate_id<>?")) {
            ps.setString(1, email);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public boolean mobileExists(String mobile, int excludeId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM candidates WHERE mobile=? AND candidate_id<>?")) {
            ps.setString(1, mobile);
            ps.setInt(2, excludeId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private Candidate map(ResultSet rs) throws SQLException {
        return new Candidate(rs.getInt("candidate_id"), rs.getString("name"), rs.getString("email"),
                rs.getString("mobile"), rs.getString("technology"), rs.getDouble("percentage"),
                rs.getInt("passing_year"), rs.getInt("backlogs"), rs.getBoolean("active"));
    }
}
