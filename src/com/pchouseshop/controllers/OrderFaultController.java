package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderFaultDAO;
import com.pchouseshop.model.OrderFault;
import com.pchouseshop.model.OrderModel;
import java.util.List;

public class OrderFaultController {

    private final OrderFaultDAO ORDER_FAULT_DAO = new OrderFaultDAO();

    public Integer addOrderFaultDAO(OrderFault pOrderFault) {
        return ORDER_FAULT_DAO.addOrderFaultDAO(pOrderFault);
    }

    public List<OrderFault> getOrderFaultController(OrderModel pOrder) {
        return ORDER_FAULT_DAO.getOrderFaultDAO(pOrder);
    }
}
