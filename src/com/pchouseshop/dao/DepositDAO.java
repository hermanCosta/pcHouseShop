package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Deposit;
import com.pchouseshop.model.OrderModel;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DepositDAO {

    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    List<Deposit> _orderDeposit;

    public long addDepositDAO(Deposit pDeposit) {
        long idDepositAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idDepositAdded = (long) _session.save(pDeposit);

            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idDepositAdded;
    }

    public List<Deposit> getOrderDepositDAO(OrderModel pOrder) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Deposit D WHERE D.order = :pOrder")
                    .setParameter("pOrder", pOrder);

            _orderDeposit =  query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _orderDeposit;
    }
}
