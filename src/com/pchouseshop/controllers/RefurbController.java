package com.pchouseshop.controllers;

import com.pchouseshop.dao.RefurbDAO;
import com.pchouseshop.model.Refurb;
import java.util.List;

public class RefurbController {

    private final RefurbDAO REFURB_DAO = new RefurbDAO();

    public List<Refurb> getAllRefurbProdController(int pIdCompany) {
        return REFURB_DAO.getAllRefurbProdDAO(pIdCompany);
    }

    public int addRefurbProductController(Refurb pRefurb) {
        return REFURB_DAO.addRefurbProductDAO(pRefurb);
    }

    public Refurb getItemRefurbProdController(int idRefurbProd) {
        return REFURB_DAO.getItemRefurbProdDAO(idRefurbProd);
    }

    public boolean updateRefurbProdController(Refurb pRefurbProd) {
        return REFURB_DAO.updateRefurbProdDAO(pRefurbProd);
    }

    public boolean deleteRefurbProdController(Refurb pRefurbProd) {
        return REFURB_DAO.deleteRefurbProdDAO(pRefurbProd);
    }
    
    public List<Refurb> searchRefurbController(String pSearch) {
        return REFURB_DAO.searchRefurbDAO(pSearch);
    }
}
