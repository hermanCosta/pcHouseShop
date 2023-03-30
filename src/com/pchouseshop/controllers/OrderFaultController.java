package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderFaultDAO;
import com.pchouseshop.model.OrderFault;
import com.pchouseshop.model.OrderModel;
import java.util.List;

public class OrderFaultController {

    private final OrderFaultDAO ORDER_FAULT_DAO = new OrderFaultDAO();

    public long addOrderFaultController(OrderFault pOrderFault) {
        return ORDER_FAULT_DAO.addOrderFaultDAO(pOrderFault);
    }

    public List<OrderFault> getOrderFaultController(OrderModel pOrder) {
        return ORDER_FAULT_DAO.getOrderFaultDAO(pOrder);
    }

    public boolean updateOrderFaultController(OrderFault pOrderFault) {
        return ORDER_FAULT_DAO.updateOrderFaultDAO(pOrderFault);
    }

    public int deleteOrderFaultController(long pIdOrderFault) {
        return ORDER_FAULT_DAO.deleteOrderFaultDAO(pIdOrderFault);
    }
}
