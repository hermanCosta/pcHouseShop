package com.pchouseshop.controllers;

import com.pchouseshop.dao.OrderNoteDAO;
import com.pchouseshop.model.OrderModel;
import com.pchouseshop.model.OrderNote;
import java.util.List;

public class OrderNoteController {

    private final OrderNoteDAO ORDER_NOTE_DAO = new OrderNoteDAO();

    public long addOrderNoteController(OrderNote pOrderNote) {
        return ORDER_NOTE_DAO.addOrderNoteDAO(pOrderNote);
    }

    public List<OrderNote> getAllOrderNoteController(OrderModel pOrder) {
        return ORDER_NOTE_DAO.getAllOrderNoteDAO(pOrder);
    }

    public Long checkExistingOrderNoteController(String pNote, OrderModel pOrder) {
        return ORDER_NOTE_DAO.checkExistingOrderNoteDAO(pNote, pOrder);
    }
    
    public List<OrderNote> searchOrderNoteController(OrderModel pOrder, String pSearch) {
        return ORDER_NOTE_DAO.searchOrderNoteDAO(pOrder, pSearch);
    }
}
