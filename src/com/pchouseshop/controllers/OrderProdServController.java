package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderProdServDAO;
import com.pchouseshop.model.OrderModel;
import com.pchouseshop.model.OrderProdServ;
import java.util.List;

public class OrderProdServController {

    private final OrderProdServDAO ORDER_PROD_SERV_DAO = new OrderProdServDAO();

    public long addOrderProdServController(OrderProdServ pOrderProdServ) {
        return ORDER_PROD_SERV_DAO.addOrderProdServDAO(pOrderProdServ);
    }

    public List<OrderProdServ> getOrderProdServController(OrderModel pOrder) {
        return ORDER_PROD_SERV_DAO.getOrderProdServDAO(pOrder);
    }

    public int deleteOrderProdServController(Integer pIdOrderProdServ) {
        return ORDER_PROD_SERV_DAO.deleteOrderProdServDAO(pIdOrderProdServ);
    }
}
