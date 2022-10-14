package com.pchouseshop.controllers;

import com.pchouseshop.dao.CompanyDAO;
import com.pchouseshop.model.Company;
import java.util.List;

public class CompanyController {
    private final CompanyDAO COMPANY_DAO = new CompanyDAO();
    
    public List<Company> getAllCompanyController() {
        return COMPANY_DAO.getAllCompanyDAO();
    }
    
    public Company getCompanyController(String name, String password) {
        return COMPANY_DAO.getCompanyDAO(name, password);
    }
}
