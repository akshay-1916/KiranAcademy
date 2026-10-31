package service;

import dao.CompanyDAO;
import dao.JobDAO;
import exception.JobNotFoundException;
import model.Job;
import util.ActivityLogger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class JobService {
    private final JobDAO jobDAO = new JobDAO();
    private final CompanyDAO companyDAO = new CompanyDAO();

    public int createJob(int companyId, String title, String technology, int openings, String location,
                         double pkg, double minPercentage, int passingYear, int maxBacklogs,
                         LocalDate deadline) throws SQLException {
        if (companyDAO.findById(companyId) == null) {
            throw new IllegalArgumentException("Company not found with id " + companyId);
        }
        Job job = new Job(0, companyId, title, technology, openings, location, pkg, minPercentage,
                passingYear, maxBacklogs, deadline, "OPEN");
        validate(job);
        int id = jobDAO.add(job);
        ActivityLogger.log("Job Created: JOB" + id);
        return id;
    }

    public void updateJob(int id, String title, String technology, int openings, String location,
                          double pkg, double minPercentage, int passingYear, int maxBacklogs,
                          LocalDate deadline) throws SQLException, JobNotFoundException {
        Job old = getJob(id);
        Job job = new Job(id, old.getCompanyId(), title, technology, openings, location, pkg,
                minPercentage, passingYear, maxBacklogs, deadline, old.getStatus());
        validate(job);
        jobDAO.update(job);
        ActivityLogger.log("Job Updated: JOB" + id);
    }

    public Job getJob(int id) throws SQLException, JobNotFoundException {
        Job job = jobDAO.findById(id);
        if (job == null) {
            throw new JobNotFoundException("Job not found with id " + id);
        }
        return job;
    }

    public List<Job> getOpenJobs() throws SQLException {
        return jobDAO.findOpen();
    }

    public List<Job> searchJobs(String keyword) throws SQLException {
        return jobDAO.search(keyword);
    }

    public void closeJob(int id) throws SQLException, JobNotFoundException {
        getJob(id);
        jobDAO.close(id);
        ActivityLogger.log("Job Closed: JOB" + id);
    }

    private void validate(Job j) {
        if (j.getTitle() == null || j.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("Job title cannot be empty");
        }
        if (j.getOpenings() <= 0) {
            throw new IllegalArgumentException("Openings must be greater than 0");
        }
        if (j.getPkg() <= 0) {
            throw new IllegalArgumentException("Package must be greater than 0");
        }
        if (j.getMinPercentage() < 0 || j.getMinPercentage() > 100) {
            throw new IllegalArgumentException("Minimum percentage must be between 0 and 100");
        }
        if (j.getMaxBacklogs() < 0) {
            throw new IllegalArgumentException("Maximum backlogs cannot be negative");
        }
        if (j.getDeadline().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Deadline cannot be in the past");
        }
    }
}
