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
}
