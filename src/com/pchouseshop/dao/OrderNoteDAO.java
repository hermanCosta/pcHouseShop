package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.OrderModel;
import com.pchouseshop.model.OrderNote;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class OrderNoteDAO {
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    List<OrderNote> _listOrderNote;
    
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
    
    public List<OrderNote> getOrderNoteDAO(OrderModel pOrder) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM OrderNote O WHERE O.order = :pOrder ORDER BY O.created ASC")
                    .setParameter("pOrder", pOrder);
            
            _listOrderNote = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listOrderNote;
    }
}
