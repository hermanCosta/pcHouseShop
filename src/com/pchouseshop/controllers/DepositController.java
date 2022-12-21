package com.pchouseshop.controllers;

import com.pchouseshop.dao.DepositDAO;
import com.pchouseshop.model.Deposit;

public class DepositController {
    private final DepositDAO DEPOSIT_DAO = new DepositDAO();
    
    public Integer addDepositController(Deposit pDeposit) {
        return  DEPOSIT_DAO.addDepositDAO(pDeposit);
    }
}
