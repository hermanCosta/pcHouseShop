package com.pchouseshop.controllers;

import com.pchouseshop.dao.CustomerDAO;
import com.pchouseshop.model.Company;
import com.pchouseshop.model.Customer;
import com.pchouseshop.model.Person;
import java.util.List;

public class CustomerController {

    private final CustomerDAO CUSTOMER_DAO = new CustomerDAO();

    public List<Customer> getAllCustomerController(Company company) {
        return CUSTOMER_DAO.getAllCustomerDAO(company);
    }

    public int addCustomerController(Customer pCustomer) {
        return CUSTOMER_DAO.addCustomerDAO(pCustomer);
    }

    public boolean updateCustomerController(Customer pCustomer) {
        return CUSTOMER_DAO.updateCustomerDAO(pCustomer);
    }

    public boolean deleteCustomerController(int pId) {
        return CUSTOMER_DAO.deleteCustomerDAO(pId);
    }

    public Customer getCustomerController(Person pPerson) {
        return CUSTOMER_DAO.getCustomerDAO(pPerson);
    }
    
    public Customer getItemCustomerController(int pIdCustomer) {
        return CUSTOMER_DAO.getItemCustomerDAO(pIdCustomer);
    }
}
