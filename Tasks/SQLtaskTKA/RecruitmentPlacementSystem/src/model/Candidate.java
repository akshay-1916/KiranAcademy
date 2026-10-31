package model;

public class Candidate {
    private int id;
    private String name;
    private String email;
    private String mobile;
    private String technology;
    private double percentage;
    private int passingYear;
    private int backlogs;
    private boolean active;

    public Candidate(int id, String name, String email, String mobile, String technology,
                     double percentage, int passingYear, int backlogs, boolean active) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.technology = technology;
        this.percentage = percentage;
        this.passingYear = passingYear;
        this.backlogs = backlogs;
        this.active = active;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getMobile() { return mobile; }
    public String getTechnology() { return technology; }
    public double getPercentage() { return percentage; }
    public int getPassingYear() { return passingYear; }
    public int getBacklogs() { return backlogs; }
    public boolean isActive() { return active; }

    @Override
    public String toString() {
        return String.format("%-6d %-20s %-26s %-12s %-12s %6.2f %6d %4d %s",
                id, name, email, mobile, technology, percentage, passingYear, backlogs,
                active ? "ACTIVE" : "INACTIVE");
    }
}
