package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class OrderDAO {
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    
    public Integer getLastOrderIdDAO() {
        Integer orderId = 0;
        
        try {
            _session = _sessionFactory.openSession();
            Transaction transaction = _session.beginTransaction();
            Query query = _session.createQuery("SELECT MAX(O.idOrder) FROM RepairOrder O");

            orderId = (Integer) query.uniqueResult();
            transaction.commit();
            
            if (orderId == null) {
                orderId = 1;
            }
            
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return orderId;
    }
}
