package service;

import dao.JobDAO;
import dao.ReportDAO;
import model.Job;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class ReportService {
    private final ReportDAO reportDAO = new ReportDAO();
    private final JobDAO jobDAO = new JobDAO();

    public Map<String, Integer> candidatesByTechnology() throws SQLException {
        return reportDAO.candidatesByTechnology();
    }

    public List<Job> openJobs() throws SQLException {
        return jobDAO.findOpen();
    }

    public Map<String, Integer> applicationsByStatus() throws SQLException {
        return reportDAO.applicationsByStatus();
    }

    public Map<String, Integer> companyWisePlacements() throws SQLException {
        return reportDAO.placementsByCompany();
    }

    public List<String> placedCandidates() throws SQLException {
        return reportDAO.placedCandidates();
    }

    public List<String> unplacedCandidates() throws SQLException {
        return reportDAO.unplacedCandidates();
    }
}
