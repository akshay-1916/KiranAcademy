package dao;

import util.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ReportDAO {

    public Map<String, Integer> candidatesByTechnology() throws SQLException {
        return countMap("SELECT technology, COUNT(*) FROM candidates GROUP BY technology ORDER BY technology");
    }

    public Map<String, Integer> applicationsByStatus() throws SQLException {
        return countMap("SELECT status, COUNT(*) FROM applications GROUP BY status ORDER BY status");
    }

    public Map<String, Integer> placementsByCompany() throws SQLException {
        return countMap("SELECT c.name, COUNT(*) FROM placements p JOIN companies c ON p.company_id=c.company_id GROUP BY c.name ORDER BY c.name");
    }

    public List<String> placedCandidates() throws SQLException {
        return stringList("SELECT c.candidate_id, c.name, co.name, p.designation, p.final_package FROM placements p JOIN candidates c ON p.candidate_id=c.candidate_id JOIN companies co ON p.company_id=co.company_id ORDER BY c.candidate_id", 5);
    }

    public List<String> unplacedCandidates() throws SQLException {
        return stringList("SELECT candidate_id, name, technology, percentage FROM candidates WHERE active=1 AND candidate_id NOT IN (SELECT candidate_id FROM placements) ORDER BY candidate_id", 4);
    }

    private Map<String, Integer> countMap(String sql) throws SQLException {
        Map<String, Integer> map = new LinkedHashMap<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                map.put(rs.getString(1), rs.getInt(2));
            }
        }
        return map;
    }

    private List<String> stringList(String sql, int columns) throws SQLException {
        List<String> list = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                StringBuilder sb = new StringBuilder();
                for (int i = 1; i <= columns; i++) {
                    if (i > 1) {
                        sb.append(" | ");
                    }
                    sb.append(rs.getString(i));
                }
                list.add(sb.toString());
            }
        }
        return list;
    }
}
