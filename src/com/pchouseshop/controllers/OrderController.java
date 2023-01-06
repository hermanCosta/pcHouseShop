package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderDAO;
import com.pchouseshop.model.OrderModel;

public class OrderController {
    private final OrderDAO ORDER_DAO = new OrderDAO();
    
    public int getLastOrderIdController() {
        return ORDER_DAO.getLastOrderIdDAO();
    }
    
    public Integer addOrderController(OrderModel pOrder) {
        return ORDER_DAO.addOrderDAO(pOrder);
    }
    
    public OrderModel getItemOrderController(int pIdOrder) {
        return ORDER_DAO.getItemOrderDAO(pIdOrder);
    }
}
