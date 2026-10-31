package model;

import java.time.LocalDate;

public class Interview {
    private int id;
    private int applicationId;
    private int round;
    private LocalDate date;
    private String mode;
    private String interviewer;
    private String status;
    private int technicalRating;
    private int communicationRating;
    private String remarks;
    private String result;

    public Interview(int id, int applicationId, int round, LocalDate date, String mode, String interviewer,
                     String status, int technicalRating, int communicationRating, String remarks, String result) {
        this.id = id;
        this.applicationId = applicationId;
        this.round = round;
        this.date = date;
        this.mode = mode;
        this.interviewer = interviewer;
        this.status = status;
        this.technicalRating = technicalRating;
        this.communicationRating = communicationRating;
        this.remarks = remarks;
        this.result = result;
    }

    public int getId() { return id; }
    public int getApplicationId() { return applicationId; }
    public int getRound() { return round; }
    public LocalDate getDate() { return date; }
    public String getMode() { return mode; }
    public String getInterviewer() { return interviewer; }
    public String getStatus() { return status; }
    public int getTechnicalRating() { return technicalRating; }
    public int getCommunicationRating() { return communicationRating; }
    public String getRemarks() { return remarks; }
    public String getResult() { return result; }

    @Override
    public String toString() {
        return String.format("INT%-4d APP%-5d Round:%d %s %-8s %-14s %-10s Tech:%d Comm:%d %s %s",
                id, applicationId, round, date, mode, interviewer, status,
                technicalRating, communicationRating, result, remarks);
    }
}
