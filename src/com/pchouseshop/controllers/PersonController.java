package com.pchouseshop.controllers;

import com.pchouseshop.dao.PersonDAO;
import com.pchouseshop.model.Person;
import java.util.List;

public class PersonController {
    private final PersonDAO PERSON_DAO = new PersonDAO();
    
    public List<Person> getAllPersonController() {
        return PERSON_DAO.getAllPersonDAO();
    }
    
    public long addPersonController(Person pPerson) {
        return PERSON_DAO.addPersonDAO(pPerson);
    }
    
    public boolean updatePersonController(Person pPerson) {
        return PERSON_DAO.updatePersonDAO(pPerson);
    }
    
    public boolean deletePersonController(Person pPerson) {
        return PERSON_DAO.deletePersonDAO(pPerson);
    }
    
    public List<Person> searchPerson(String pSearch) {
        return PERSON_DAO.searchPersonDAO(pSearch);
    }
    
    public Person searchPesonByContactNoController(String pContactNo) {
        return PERSON_DAO.searchPesonByContactNoDAO(pContactNo);
    }
}
