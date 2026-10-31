package model;

import java.time.LocalDate;

public class Job {
    private int id;
    private int companyId;
    private String title;
    private String technology;
    private int openings;
    private String location;
    private double pkg;
    private double minPercentage;
    private int passingYear;
    private int maxBacklogs;
    private LocalDate deadline;
    private String status;

    public Job(int id, int companyId, String title, String technology, int openings, String location,
               double pkg, double minPercentage, int passingYear, int maxBacklogs,
               LocalDate deadline, String status) {
        this.id = id;
        this.companyId = companyId;
        this.title = title;
        this.technology = technology;
        this.openings = openings;
        this.location = location;
        this.pkg = pkg;
        this.minPercentage = minPercentage;
        this.passingYear = passingYear;
        this.maxBacklogs = maxBacklogs;
        this.deadline = deadline;
        this.status = status;
    }

    public int getId() { return id; }
    public int getCompanyId() { return companyId; }
    public String getTitle() { return title; }
    public String getTechnology() { return technology; }
    public int getOpenings() { return openings; }
    public String getLocation() { return location; }
    public double getPkg() { return pkg; }
    public double getMinPercentage() { return minPercentage; }
    public int getPassingYear() { return passingYear; }
    public int getMaxBacklogs() { return maxBacklogs; }
    public LocalDate getDeadline() { return deadline; }
    public String getStatus() { return status; }

    @Override
    public String toString() {
        return String.format("%-5d Co:%-4d %-22s %-10s Open:%-3d %-10s %5.1fLPA Min:%5.1f%% Year:%d MaxBL:%d %s %s",
                id, companyId, title, technology, openings, location, pkg, minPercentage,
                passingYear, maxBacklogs, deadline, status);
    }
}
