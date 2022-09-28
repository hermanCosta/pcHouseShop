package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderDAO;

public class OrderController {
    private final OrderDAO ORDER_DAO = new OrderDAO();
    
    public int getLastOrderIdController() {
        return ORDER_DAO.getLastOrderIdDAO();
    }
}
