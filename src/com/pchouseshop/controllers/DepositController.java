package com.pchouseshop.controllers;

import com.pchouseshop.dao.DepositDAO;
import com.pchouseshop.model.Deposit;
import com.pchouseshop.model.OrderModel;
import java.util.List;

public class DepositController {

    private final DepositDAO DEPOSIT_DAO = new DepositDAO();

    public Integer addDepositController(Deposit pDeposit) {
        return DEPOSIT_DAO.addDepositDAO(pDeposit);
    }

    public List<Deposit> getOrderDepositController(OrderModel pOrder) {
        return DEPOSIT_DAO.getOrderDepositDAO(pOrder);
    }
}
