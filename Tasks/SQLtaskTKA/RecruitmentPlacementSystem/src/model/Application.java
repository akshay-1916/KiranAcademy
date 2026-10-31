package model;

import java.time.LocalDate;

public class Application {
    private int id;
    private int candidateId;
    private int jobId;
    private String status;
    private LocalDate appliedDate;

    public Application(int id, int candidateId, int jobId, String status, LocalDate appliedDate) {
        this.id = id;
        this.candidateId = candidateId;
        this.jobId = jobId;
        this.status = status;
        this.appliedDate = appliedDate;
    }

    public int getId() { return id; }
    public int getCandidateId() { return candidateId; }
    public int getJobId() { return jobId; }
    public String getStatus() { return status; }
    public LocalDate getAppliedDate() { return appliedDate; }

    @Override
    public String toString() {
        return String.format("APP%-5d Candidate:%-6d Job:%-5d %-12s %s", id, candidateId, jobId, status, appliedDate);
    }
}
