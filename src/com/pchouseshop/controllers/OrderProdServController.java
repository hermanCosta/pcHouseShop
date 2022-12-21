package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderProdServDAO;
import com.pchouseshop.model.OrderProdServ;

public class OrderProdServController {
    private final OrderProdServDAO ORDER_PROD_SERV_DAO = new OrderProdServDAO();
    
    public Integer addOrderProdServController(OrderProdServ pOrderProdServ) {
        return ORDER_PROD_SERV_DAO.addOrderProdServDAO(pOrderProdServ);
    }
}
