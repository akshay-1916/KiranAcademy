package dao;

import model.Job;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class JobDAO {

    public int add(Job j) throws SQLException {
        String sql = "INSERT INTO jobs(company_id, title, technology, openings, location, package_lpa, min_percentage, passing_year, max_backlogs, deadline, status) VALUES(?,?,?,?,?,?,?,?,?,?,'OPEN')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, j.getCompanyId());
            ps.setString(2, j.getTitle());
            ps.setString(3, j.getTechnology());
            ps.setInt(4, j.getOpenings());
            ps.setString(5, j.getLocation());
            ps.setDouble(6, j.getPkg());
            ps.setDouble(7, j.getMinPercentage());
            ps.setInt(8, j.getPassingYear());
            ps.setInt(9, j.getMaxBacklogs());
            ps.setDate(10, Date.valueOf(j.getDeadline()));
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int update(Job j) throws SQLException {
        String sql = "UPDATE jobs SET title=?, technology=?, openings=?, location=?, package_lpa=?, min_percentage=?, passing_year=?, max_backlogs=?, deadline=? WHERE job_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, j.getTitle());
            ps.setString(2, j.getTechnology());
            ps.setInt(3, j.getOpenings());
            ps.setString(4, j.getLocation());
            ps.setDouble(5, j.getPkg());
            ps.setDouble(6, j.getMinPercentage());
            ps.setInt(7, j.getPassingYear());
            ps.setInt(8, j.getMaxBacklogs());
            ps.setDate(9, Date.valueOf(j.getDeadline()));
            ps.setInt(10, j.getId());
            return ps.executeUpdate();
        }
    }

    public int close(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("UPDATE jobs SET status='CLOSED' WHERE job_id=?")) {
            ps.setInt(1, id);
            return ps.executeUpdate();
        }
    }

    public void reduceOpening(int id) throws SQLException {
        String sql = "UPDATE jobs SET openings = openings - 1, status = CASE WHEN openings <= 0 THEN 'CLOSED' ELSE status END WHERE job_id=? AND openings > 0";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Job findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM jobs WHERE job_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public List<Job> findOpen() throws SQLException {
        List<Job> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM jobs WHERE status='OPEN' ORDER BY job_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    public List<Job> search(String keyword) throws SQLException {
        List<Job> list = new ArrayList<>();
        String sql = "SELECT * FROM jobs WHERE title LIKE ? OR technology LIKE ? OR location LIKE ? ORDER BY job_id";
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

    private Job map(ResultSet rs) throws SQLException {
        return new Job(rs.getInt("job_id"), rs.getInt("company_id"), rs.getString("title"),
                rs.getString("technology"), rs.getInt("openings"), rs.getString("location"),
                rs.getDouble("package_lpa"), rs.getDouble("min_percentage"), rs.getInt("passing_year"),
                rs.getInt("max_backlogs"), rs.getDate("deadline").toLocalDate(), rs.getString("status"));
    }
}
