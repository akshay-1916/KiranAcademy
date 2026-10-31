package model;

import java.time.LocalDate;

public class Placement {
    private int id;
    private int applicationId;
    private int candidateId;
    private int companyId;
    private String designation;
    private double finalPackage;
    private LocalDate joiningDate;
    private String joiningStatus;

    public Placement(int id, int applicationId, int candidateId, int companyId, String designation,
                     double finalPackage, LocalDate joiningDate, String joiningStatus) {
        this.id = id;
        this.applicationId = applicationId;
        this.candidateId = candidateId;
        this.companyId = companyId;
        this.designation = designation;
        this.finalPackage = finalPackage;
        this.joiningDate = joiningDate;
        this.joiningStatus = joiningStatus;
    }

    public int getId() { return id; }
    public int getApplicationId() { return applicationId; }
    public int getCandidateId() { return candidateId; }
    public int getCompanyId() { return companyId; }
    public String getDesignation() { return designation; }
    public double getFinalPackage() { return finalPackage; }
    public LocalDate getJoiningDate() { return joiningDate; }
    public String getJoiningStatus() { return joiningStatus; }

    @Override
    public String toString() {
        return String.format("PLC%-4d APP%-5d Candidate:%-6d Company:%-4d %-20s %5.1fLPA %s %s",
                id, applicationId, candidateId, companyId, designation, finalPackage, joiningDate, joiningStatus);
    }
}
