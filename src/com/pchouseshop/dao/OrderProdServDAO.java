package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.OrderModel;
import com.pchouseshop.model.OrderProdServ;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class OrderProdServDAO {

    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    List<OrderProdServ> _listOrderProdServ;

    public Integer addOrderProdServDAO(OrderProdServ pOrderProdServ) {
        Integer idOrderProdServAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idOrderProdServAdded = (Integer) _session.save(pOrderProdServ);

            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idOrderProdServAdded;
    }

    public List<OrderProdServ> getOrderProdServDAO(OrderModel pOrder) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM OrderProdServ O WHERE O.order = :pOrder")
                    .setParameter("pOrder", pOrder);

            _listOrderProdServ = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listOrderProdServ;
    }
}
