package dao;

import model.Placement;
import util.DBConnection;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PlacementDAO {

    public int add(Placement p) throws SQLException {
        String sql = "INSERT INTO placements(application_id, candidate_id, company_id, designation, final_package, joining_date, joining_status) VALUES(?,?,?,?,?,?,?)";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, p.getApplicationId());
            ps.setInt(2, p.getCandidateId());
            ps.setInt(3, p.getCompanyId());
            ps.setString(4, p.getDesignation());
            ps.setDouble(5, p.getFinalPackage());
            ps.setDate(6, Date.valueOf(p.getJoiningDate()));
            ps.setString(7, p.getJoiningStatus());
            ps.executeUpdate();
            try (ResultSet rs = ps.getGeneratedKeys()) {
                rs.next();
                return rs.getInt(1);
            }
        }
    }

    public List<Placement> findAll() throws SQLException {
        List<Placement> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT * FROM placements ORDER BY placement_id");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new Placement(rs.getInt("placement_id"), rs.getInt("application_id"),
                        rs.getInt("candidate_id"), rs.getInt("company_id"), rs.getString("designation"),
                        rs.getDouble("final_package"), rs.getDate("joining_date").toLocalDate(),
                        rs.getString("joining_status")));
            }
        }
        return list;
    }
}
