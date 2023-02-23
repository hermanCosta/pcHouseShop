package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.OrderFault;
import com.pchouseshop.model.OrderModel;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class OrderFaultDAO {
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    List<OrderFault> _listOrderFault;
    
    public Integer addOrderFaultDAO(OrderFault pOrderFault) {
        Integer idOrderFaultAdded = 0;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idOrderFaultAdded = (Integer) _session.save(pOrderFault);

            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
         
        return idOrderFaultAdded;
    }
    
    public List<OrderFault> getOrderFaultDAO(OrderModel pOrder) {
        try {
             _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM OrderFault O WHERE O.order = :pOrder")
                    .setParameter("pOrder", pOrder);

            _listOrderFault = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return _listOrderFault;
    }
}
