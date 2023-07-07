package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderPaymentDAO;
import com.pchouseshop.model.OrderPayment;

public class OrderPaymentController {

    private final OrderPaymentDAO ORDER_PAYMENT_DAO = new OrderPaymentDAO();

    public long addOrderPaymentController(OrderPayment pOrderPayment) {
        return ORDER_PAYMENT_DAO.addOrderPaymentDAO(pOrderPayment);
    }

}
