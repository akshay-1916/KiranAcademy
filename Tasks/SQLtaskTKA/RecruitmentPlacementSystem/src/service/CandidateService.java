package service;

import dao.CandidateDAO;
import exception.CandidateNotFoundException;
import model.Candidate;
import util.ActivityLogger;

import java.sql.SQLException;
import java.util.List;

public class CandidateService {
    private final CandidateDAO candidateDAO = new CandidateDAO();

    public int addCandidate(String name, String email, String mobile, String technology,
                            double percentage, int passingYear, int backlogs) throws SQLException {
        Candidate c = new Candidate(0, name, email, mobile, technology, percentage, passingYear, backlogs, true);
        validate(c);
        int id = candidateDAO.add(c);
        ActivityLogger.log("Candidate Created: CAN" + id);
        return id;
    }

    public void updateCandidate(int id, String name, String email, String mobile, String technology,
                                double percentage, int passingYear, int backlogs)
            throws SQLException, CandidateNotFoundException {
        getCandidate(id);
        Candidate c = new Candidate(id, name, email, mobile, technology, percentage, passingYear, backlogs, true);
        validate(c);
        candidateDAO.update(c);
        ActivityLogger.log("Candidate Updated: CAN" + id);
    }

    public Candidate getCandidate(int id) throws SQLException, CandidateNotFoundException {
        Candidate c = candidateDAO.findById(id);
        if (c == null) {
            throw new CandidateNotFoundException("Candidate not found with id " + id);
        }
        return c;
    }

    public List<Candidate> getAllCandidates() throws SQLException {
        return candidateDAO.findAll();
    }

    public List<Candidate> searchCandidates(String keyword) throws SQLException {
        return candidateDAO.search(keyword);
    }

    public void deactivateCandidate(int id) throws SQLException, CandidateNotFoundException {
        getCandidate(id);
        candidateDAO.deactivate(id);
        ActivityLogger.log("Candidate Deactivated: CAN" + id);
    }

    private void validate(Candidate c) throws SQLException {
        if (c.getName() == null || c.getName().trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be empty");
        }
        if (c.getEmail() == null || !c.getEmail().matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        if (c.getMobile() == null || !c.getMobile().matches("\\d{10}")) {
            throw new IllegalArgumentException("Mobile must be exactly 10 digits");
        }
        if (c.getTechnology() == null || c.getTechnology().trim().isEmpty()) {
            throw new IllegalArgumentException("Technology cannot be empty");
        }
        if (c.getPercentage() < 0 || c.getPercentage() > 100) {
            throw new IllegalArgumentException("Percentage must be between 0 and 100");
        }
        if (c.getBacklogs() < 0) {
            throw new IllegalArgumentException("Backlog count cannot be negative");
        }
        if (c.getPassingYear() < 2000 || c.getPassingYear() > 2100) {
            throw new IllegalArgumentException("Invalid passing year");
        }
        if (candidateDAO.emailExists(c.getEmail(), c.getId())) {
            throw new IllegalArgumentException("Duplicate email not allowed");
        }
        if (candidateDAO.mobileExists(c.getMobile(), c.getId())) {
            throw new IllegalArgumentException("Duplicate mobile not allowed");
        }
    }
}
