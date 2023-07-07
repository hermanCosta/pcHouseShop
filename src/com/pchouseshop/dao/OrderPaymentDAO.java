package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.OrderPayment;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

public class OrderPaymentDAO {

    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;

    public long addOrderPaymentDAO(OrderPayment pOrderPayment) {
        long idOrderPaymentAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idOrderPaymentAdded = (long) _session.save(pOrderPayment);

            _transaction.commit();
        } catch (HibernateException e) {
            System.out.println("Error Hibernate: " + e.getMessage());
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idOrderPaymentAdded;
    }
}
