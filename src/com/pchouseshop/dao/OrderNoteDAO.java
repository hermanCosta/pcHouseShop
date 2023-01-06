package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.OrderNote;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OrderNoteDAO {
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    
    public Integer addOrderNoteDAO(OrderNote pOrderNote) {
        Integer idOrderNoteAdded = 0;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idOrderNoteAdded = (Integer) _session.save(pOrderNote);

            _transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Error Hibernate: " + e.getMessage());
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
         
        return idOrderNoteAdded;
    }
}
