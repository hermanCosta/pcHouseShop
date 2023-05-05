package com.pchouseshop.controllers;

import com.pchouseshop.dao.FaultDAO;
import com.pchouseshop.model.Fault;
import java.util.List;

public class FaultController {

    private final FaultDAO FAULT_DAO = new FaultDAO();

    public List<Fault> getAllFaultController() {
        return FAULT_DAO.getAllFaultDAO();
    }

    public long addFaultController(Fault pFault) {
        return FAULT_DAO.addFaultDAO(pFault);
    }

    public boolean updateFaultController(Fault pFault) {
        return FAULT_DAO.updateFaultDAO(pFault);
    }

    public boolean deleteFaultController(Fault pFault) {
        return FAULT_DAO.deleteFaultDAO(pFault);
    }

    public List<Fault> searchFault(String pSearch) {
        return FAULT_DAO.searchFaultDAO(pSearch);
    }

    public Fault getItemFaultController(long pIdFault) {
        return FAULT_DAO.getItemFaultDAO(pIdFault);
    }

    public List<Fault> orderSearchFaultController(String pSearch) {
        return FAULT_DAO.orderSearchFaultDAO(pSearch);
    }

    public long checkExistFaultDAO(String pSearch) {
        return FAULT_DAO.checkExistFaultDAO(pSearch);
    }
}
