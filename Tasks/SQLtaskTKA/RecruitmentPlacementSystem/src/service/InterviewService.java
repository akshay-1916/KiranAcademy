package service;

import dao.InterviewDAO;
import model.Application;
import model.Interview;
import util.ActivityLogger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class InterviewService {
    private final InterviewDAO interviewDAO = new InterviewDAO();
    private final ApplicationService applicationService = new ApplicationService();

    public int schedule(int applicationId, LocalDate date, String mode, String interviewer) throws SQLException {
        Application a = applicationService.getApplication(applicationId);
        if (!a.getStatus().equals("SHORTLISTED") && !a.getStatus().equals("INTERVIEW")) {
            throw new IllegalStateException("Interview can be scheduled only for SHORTLISTED or INTERVIEW applications, current status is " + a.getStatus());
        }
        if (date.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Interview date cannot be in the past");
        }
        if (interviewer == null || interviewer.trim().isEmpty()) {
            throw new IllegalArgumentException("Interviewer name cannot be empty");
        }
        int round = interviewDAO.countByApplication(applicationId) + 1;
        Interview i = new Interview(0, applicationId, round, date, mode, interviewer, "SCHEDULED", 0, 0, null, null);
        int id = interviewDAO.add(i);
        if (a.getStatus().equals("SHORTLISTED")) {
            applicationService.changeStatus(applicationId, "INTERVIEW");
        }
        ActivityLogger.log("Interview Scheduled: INT" + id + " for APP" + applicationId);
        return id;
    }

    public void saveFeedback(int interviewId, int tech, int comm, String remarks, String result) throws SQLException {
        Interview i = interviewDAO.findById(interviewId);
        if (i == null) {
            throw new IllegalArgumentException("Interview not found with id " + interviewId);
        }
        if (i.getStatus().equals("COMPLETED")) {
            throw new IllegalStateException("Feedback already submitted for this interview");
        }
        if (tech < 1 || tech > 10 || comm < 1 || comm > 10) {
            throw new IllegalArgumentException("Ratings must be between 1 and 10");
        }
        result = result.toUpperCase();
        if (!result.equals("SELECTED") && !result.equals("NEXT_ROUND") && !result.equals("REJECTED")) {
            throw new IllegalArgumentException("Result must be SELECTED, NEXT_ROUND or REJECTED");
        }
        interviewDAO.saveFeedback(interviewId, tech, comm, remarks, result);
        if (result.equals("SELECTED")) {
            applicationService.changeStatus(i.getApplicationId(), "SELECTED");
        } else if (result.equals("REJECTED")) {
            applicationService.changeStatus(i.getApplicationId(), "REJECTED");
        }
        ActivityLogger.log("Interview Feedback Saved: INT" + interviewId + " result " + result);
    }

    public List<Interview> getAllInterviews() throws SQLException {
        return interviewDAO.findAll();
    }
}
