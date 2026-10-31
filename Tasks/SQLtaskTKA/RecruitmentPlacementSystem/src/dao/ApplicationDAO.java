package dao;

import model.Application;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ApplicationDAO {

    public int add(int candidateId, int jobId) throws SQLException {
        String sql = "INSERT INTO applications(candidate_id, job_id, status, applied_date) VALUES(?,?,'APPLIED',?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, candidateId);
            ps.setInt(2, jobId);
            ps.setDate(3, Date.valueOf(LocalDate.now()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public boolean exists(int candidateId, int jobId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM applications WHERE candidate_id=? AND job_id=?")) {
            ps.setInt(1, candidateId);
            ps.setInt(2, jobId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    public Application findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM applications WHERE application_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Application> findAll() throws SQLException {
        List<Application> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM applications ORDER BY application_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<Application> findByCandidate(int candidateId) throws SQLException {
        List<Application> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM applications WHERE candidate_id=? ORDER BY application_id")) {
            ps.setInt(1, candidateId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(map(rs));
                }
            }
        }
        return list;
    }

    public int updateStatus(int id, String status) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE applications SET status=? WHERE application_id=?")) {
            ps.setString(1, status);
            ps.setInt(2, id);
            return ps.executeUpdate();
        }
    }

    private Application map(ResultSet rs) throws SQLException {
        return new Application(rs.getInt("application_id"), rs.getInt("candidate_id"), rs.getInt("job_id"),
                rs.getString("status"), rs.getDate("applied_date").toLocalDate());
    }
}
