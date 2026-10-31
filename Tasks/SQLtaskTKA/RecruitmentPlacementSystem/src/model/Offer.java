package model;

import java.time.LocalDate;

public class Offer {
    private int id;
    private int applicationId;
    private String designation;
    private double pkg;
    private LocalDate offerDate;
    private LocalDate joiningDate;
    private String status;

    public Offer(int id, int applicationId, String designation, double pkg,
                 LocalDate offerDate, LocalDate joiningDate, String status) {
        this.id = id;
        this.applicationId = applicationId;
        this.designation = designation;
        this.pkg = pkg;
        this.offerDate = offerDate;
        this.joiningDate = joiningDate;
        this.status = status;
    }

    public int getId() { return id; }
    public int getApplicationId() { return applicationId; }
    public String getDesignation() { return designation; }
    public double getPkg() { return pkg; }
    public LocalDate getOfferDate() { return offerDate; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("OFR%-4d APP%-5d %-20s %5.1fLPA Offer:%s Join:%s %s",
                id, applicationId, designation, pkg, offerDate, joiningDate, status);
    }
}
