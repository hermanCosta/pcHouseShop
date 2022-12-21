package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderNoteDAO;
import com.pchouseshop.model.OrderNote;

public class OrderNoteController {
    private final OrderNoteDAO ORDER_NOTE_DAO = new OrderNoteDAO();
    
    public Integer addOrderNoteController(OrderNote pOrderNote) {
        return ORDER_NOTE_DAO.addOrderNoteDAO(pOrderNote);
    }
}
