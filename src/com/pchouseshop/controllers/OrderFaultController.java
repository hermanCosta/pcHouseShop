package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderFaultDAO;
import com.pchouseshop.model.OrderFault;

public class OrderFaultController {
    private final OrderFaultDAO ORDER_FAULT_DAO = new OrderFaultDAO();
    
    public Integer addOrderFaultDAO(OrderFault pOrderFault) {
        return ORDER_FAULT_DAO.addOrderFaultDAO(pOrderFault);
    }
}
