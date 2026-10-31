package service;

import dao.CompanyDAO;
import model.Company;
import util.ActivityLogger;

import java.sql.SQLException;
import java.util.List;

public class CompanyService {
    private final CompanyDAO companyDAO = new CompanyDAO();

    public int addCompany(String name, String industry, String location, String email) throws SQLException {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Company name cannot be empty");
        }
        if (email == null || !email.matches("^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        int id = companyDAO.add(new Company(0, name, industry, location, email));
        ActivityLogger.log("Company Created: COM" + id);
        return id;
    }

    public List<Company> getAllCompanies() throws SQLException {
        return companyDAO.findAll();
    }
}
