package model;

public class Company {
    private int id;
    private String name;
    private String industry;
    private String location;
    private String email;

    public Company(int id, String name, String industry, String location, String email) {
        this.id = id;
        this.name = name;
        this.industry = industry;
        this.location = location;
        this.email = email;
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public String getIndustry() { return industry; }
    public String getLocation() { return location; }
    public String getEmail() { return email; }

    @Override
    public String toString() {
        return String.format("%-6d %-22s %-16s %-14s %s", id, name, industry, location, email);
    }
}
