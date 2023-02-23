package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderDAO;
import com.pchouseshop.model.Company;
import com.pchouseshop.model.OrderModel;
import java.util.List;

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

    public List<OrderModel> getAllOrderController(Company pCompany) {
        return ORDER_DAO.getAllOrderDAO(pCompany);
    }
    
    public List<OrderModel> searchOrderController(Company pCompany, String pSearch) {
        return ORDER_DAO.searchOrderDAO(pCompany, pSearch);
    }
}
