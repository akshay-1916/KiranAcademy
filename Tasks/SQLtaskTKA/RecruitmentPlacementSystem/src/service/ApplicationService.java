package service;

import dao.ApplicationDAO;
import dao.CandidateDAO;
import dao.JobDAO;
import exception.CandidateNotEligibleException;
import exception.CandidateNotFoundException;
import exception.DuplicateApplicationException;
import exception.JobNotFoundException;
import model.Application;
import model.Candidate;
import model.Job;
import util.ActivityLogger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationService {
    private final ApplicationDAO applicationDAO = new ApplicationDAO();
    private final CandidateDAO candidateDAO = new CandidateDAO();
    private final JobDAO jobDAO = new JobDAO();

    private static final Map<String, String> NEXT_STATUS = new HashMap<>();

    static {
        NEXT_STATUS.put("APPLIED", "SHORTLISTED");
        NEXT_STATUS.put("SHORTLISTED", "INTERVIEW");
        NEXT_STATUS.put("INTERVIEW", "SELECTED");
        NEXT_STATUS.put("SELECTED", "OFFERED");
        NEXT_STATUS.put("OFFERED", "JOINED");
    }

    public String checkEligibility(int candidateId, int jobId)
            throws SQLException, CandidateNotFoundException, JobNotFoundException {
        Candidate c = candidateDAO.findById(candidateId);
        if (c == null) {
            throw new CandidateNotFoundException("Candidate not found with id " + candidateId);
        }
        Job j = jobDAO.findById(jobId);
        if (j == null) {
            throw new JobNotFoundException("Job not found with id " + jobId);
        }
        if (!c.isActive()) {
            return "NOT ELIGIBLE: Candidate is not active";
        }
        if (!"OPEN".equals(j.getStatus())) {
            return "NOT ELIGIBLE: Job is closed";
        }
        if (j.getDeadline().isBefore(LocalDate.now())) {
            return "NOT ELIGIBLE: Application deadline " + j.getDeadline() + " has passed";
        }
        if (applicationDAO.exists(candidateId, jobId)) {
            return "NOT ELIGIBLE: Candidate has already applied for this job";
        }
        if (c.getPercentage() < j.getMinPercentage()) {
            return "NOT ELIGIBLE: Percentage " + c.getPercentage() + " is below required " + j.getMinPercentage();
        }
        if (c.getPassingYear() != j.getPassingYear()) {
            return "NOT ELIGIBLE: Passing year " + c.getPassingYear() + " does not match required " + j.getPassingYear();
        }
        if (c.getBacklogs() > j.getMaxBacklogs()) {
            return "NOT ELIGIBLE: Backlogs " + c.getBacklogs() + " exceed allowed " + j.getMaxBacklogs();
        }
        return "ELIGIBLE";
    }

    public int apply(int candidateId, int jobId) throws SQLException, CandidateNotFoundException,
            JobNotFoundException, DuplicateApplicationException, CandidateNotEligibleException {
        String result = checkEligibility(candidateId, jobId);
        if (applicationDAO.exists(candidateId, jobId)) {
            throw new DuplicateApplicationException("Candidate " + candidateId + " has already applied for job " + jobId);
        }
        if (!result.equals("ELIGIBLE")) {
            throw new CandidateNotEligibleException(result);
        }
        int id = applicationDAO.add(candidateId, jobId);
        ActivityLogger.log("Application Created: APP" + id);
        return id;
    }

    public Application getApplication(int id) throws SQLException {
        Application a = applicationDAO.findById(id);
        if (a == null) {
            throw new IllegalArgumentException("Application not found with id " + id);
        }
        return a;
    }

    public List<Application> getAllApplications() throws SQLException {
        return applicationDAO.findAll();
    }

    public List<Application> getApplicationsOfCandidate(int candidateId) throws SQLException {
        return applicationDAO.findByCandidate(candidateId);
    }

    public void changeStatus(int applicationId, String newStatus) throws SQLException {
        Application a = getApplication(applicationId);
        String current = a.getStatus();
        newStatus = newStatus.toUpperCase();
        boolean finished = current.equals("JOINED") || current.equals("REJECTED");
        boolean validForward = newStatus.equals(NEXT_STATUS.get(current));
        boolean validReject = newStatus.equals("REJECTED") && !finished;
        if (!validForward && !validReject) {
            throw new IllegalStateException("Invalid status change from " + current + " to " + newStatus);
        }
        applicationDAO.updateStatus(applicationId, newStatus);
        if (newStatus.equals("SHORTLISTED")) {
            ActivityLogger.log("Candidate Shortlisted: APP" + applicationId);
        } else {
            ActivityLogger.log("Application Status Changed: APP" + applicationId + " " + current + " -> " + newStatus);
        }
    }
}
