package dao;

import model.Interview;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class InterviewDAO {

    public int add(Interview i) throws SQLException {
        String sql = "INSERT INTO interviews(application_id, round_no, interview_date, mode, interviewer, status) VALUES(?,?,?,?,?,'SCHEDULED')";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, i.getApplicationId());
            ps.setInt(2, i.getRound());
            ps.setDate(3, Date.valueOf(i.getDate()));
            ps.setString(4, i.getMode());
            ps.setString(5, i.getInterviewer());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public int saveFeedback(int id, int tech, int comm, String remarks, String result) throws SQLException {
        String sql = "UPDATE interviews SET technical_rating=?, communication_rating=?, remarks=?, result=?, status='COMPLETED' WHERE interview_id=?";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, tech);
            ps.setInt(2, comm);
            ps.setString(3, remarks);
            ps.setString(4, result);
            ps.setInt(5, id);
            return ps.executeUpdate();
        }
    }

    public Interview findById(int id) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM interviews WHERE interview_id=?")) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return map(rs);
                }
            }
        }
        return null;
    }

    public int countByApplication(int applicationId) throws SQLException {
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM interviews WHERE application_id=?")) {
            ps.setInt(1, applicationId);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public List<Interview> findAll() throws SQLException {
        List<Interview> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM interviews ORDER BY interview_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(map(rs));
            }
        }
        return list;
    }

    private Interview map(ResultSet rs) throws SQLException {
        String remarks = rs.getString("remarks");
        String result = rs.getString("result");
        return new Interview(rs.getInt("interview_id"), rs.getInt("application_id"), rs.getInt("round_no"),
                rs.getDate("interview_date").toLocalDate(), rs.getString("mode"), rs.getString("interviewer"),
                rs.getString("status"), rs.getInt("technical_rating"), rs.getInt("communication_rating"),
                remarks == null ? "-" : remarks, result == null ? "-" : result);
    }
}
