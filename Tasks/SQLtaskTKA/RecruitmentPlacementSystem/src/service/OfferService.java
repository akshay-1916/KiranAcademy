package service;

import dao.OfferDAO;
import model.Application;
import model.Offer;
import util.ActivityLogger;

import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class OfferService {
    private final OfferDAO offerDAO = new OfferDAO();
    private final ApplicationService applicationService = new ApplicationService();

    public int createOffer(int applicationId, String designation, double pkg, LocalDate joiningDate) throws SQLException {
        Application a = applicationService.getApplication(applicationId);
        if (!a.getStatus().equals("SELECTED")) {
            throw new IllegalStateException("Offer can be created only for SELECTED applications, current status is " + a.getStatus());
        }
        if (designation == null || designation.trim().isEmpty()) {
            throw new IllegalArgumentException("Designation cannot be empty");
        }
        if (pkg <= 0) {
            throw new IllegalArgumentException("Package must be greater than 0");
        }
        if (joiningDate.isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Joining date cannot be in the past");
        }
        Offer o = new Offer(0, applicationId, designation, pkg, LocalDate.now(), joiningDate, "OFFERED");
        int id = offerDAO.add(o);
        applicationService.changeStatus(applicationId, "OFFERED");
        ActivityLogger.log("Offer Created: OFR" + id + " for APP" + applicationId);
        return id;
    }

    public void respondToOffer(int offerId, boolean accept) throws SQLException {
        Offer o = offerDAO.findById(offerId);
        if (o == null) {
            throw new IllegalArgumentException("Offer not found with id " + offerId);
        }
        if (!o.getStatus().equals("OFFERED")) {
            throw new IllegalStateException("Offer already " + o.getStatus());
        }
        if (accept) {
            offerDAO.updateStatus(offerId, "ACCEPTED");
        } else {
            offerDAO.updateStatus(offerId, "DECLINED");
            applicationService.changeStatus(o.getApplicationId(), "REJECTED");
        }
        ActivityLogger.log("Offer " + (accept ? "Accepted" : "Declined") + ": OFR" + offerId);
    }

    public List<Offer> getAllOffers() throws SQLException {
        return offerDAO.findAll();
    }
}
