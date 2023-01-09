package carsharing.dao;

import carsharing.entity.Company;

import java.util.List;

public interface CompanyDao {
    void createCompany(String companyName);
    Company getCompany(int id);
    List<Company> getAllCompanies();
}
