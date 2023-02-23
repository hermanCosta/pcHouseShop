package com.pchouseshop.controllers;

import com.pchouseshop.dao.EmployeeDAO;
import com.pchouseshop.model.Employee;
import com.pchouseshop.model.Person;
import java.util.List;

public class EmployeeController {

    private final EmployeeDAO EMPLOYEE_DAO = new EmployeeDAO();

    public List<Employee> getAllEmployeeController() {
        return EMPLOYEE_DAO.getAllEmployeeDAO();
    }

    public int addEmployeeController(Employee pEmployee) {
        return EMPLOYEE_DAO.addEmployeeDAO(pEmployee);
    }

    public boolean updateEmployeeController(Employee pEmplyoee) {
        return EMPLOYEE_DAO.updateEmployeeDAO(pEmplyoee);
    }

    public boolean deleteEmplyoeeController(int pId) {
        return EMPLOYEE_DAO.deleteEmplyoeeDAO(pId);
    }

    public Employee getEmployeeController(Person pPerson) {
        return EMPLOYEE_DAO.getEmployeeDAO(pPerson);
    }

    public Employee getItemEmployeeController(int pIdEmployee) {
        return EMPLOYEE_DAO.getItemEmployeeDAO(pIdEmployee);
    }

    public Employee getEmployeeByPassDAO(String pPassStr) {
        return EMPLOYEE_DAO.getEmployeeByPassDAO(pPassStr);
    }

    public Employee searchEmployeeByContactNoDAO(String pContactNo) {
        return EMPLOYEE_DAO.searchEmployeeByContactNoDAO(pContactNo);
    }
}
