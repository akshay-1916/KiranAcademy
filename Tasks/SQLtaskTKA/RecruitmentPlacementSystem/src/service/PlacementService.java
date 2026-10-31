package service;

import dao.JobDAO;
import dao.OfferDAO;
import dao.PlacementDAO;
import model.Application;
import model.Job;
import model.Offer;
import model.Placement;
import util.ActivityLogger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class PlacementService {
    private final PlacementDAO placementDAO = new PlacementDAO();
    private final OfferDAO offerDAO = new OfferDAO();
    private final JobDAO jobDAO = new JobDAO();
    private final ApplicationService applicationService = new ApplicationService();

    public int confirmJoining(int applicationId, LocalDate actualJoiningDate) throws SQLException {
        Application a = applicationService.getApplication(applicationId);
        if (!a.getStatus().equals("OFFERED")) {
            throw new IllegalStateException("Joining can be confirmed only for OFFERED applications, current status is " + a.getStatus());
        }
        Offer o = offerDAO.findByApplication(applicationId);
        if (o == null || !o.getStatus().equals("ACCEPTED")) {
            throw new IllegalStateException("Offer must be accepted before joining");
        }
        Job j = jobDAO.findById(a.getJobId());
        Placement p = new Placement(0, applicationId, a.getCandidateId(), j.getCompanyId(),
                o.getDesignation(), o.getPkg(), actualJoiningDate, "JOINED");
        int id = placementDAO.add(p);
        applicationService.changeStatus(applicationId, "JOINED");
        jobDAO.reduceOpening(j.getId());
        ActivityLogger.log("Candidate Placed: PLC" + id + " APP" + applicationId);
        return id;
    }

    public List<Placement> getAllPlacements() throws SQLException {
        return placementDAO.findAll();
    }
}
