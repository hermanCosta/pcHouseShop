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

    public long addOrderFaultDAO(OrderFault pOrderFault) {
        long idOrderFaultAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idOrderFaultAdded = (long) _session.save(pOrderFault);

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

    public boolean updateOrderFaultDAO(OrderFault pOrderFault) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            _session.saveOrUpdate(pOrderFault);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }
    
    public int deleteOrderFaultDAO(long pIdOrderFault) {
        int resultDelete = 0;
        try
        {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            
            Query query = _session.createQuery("DELETE FROM OrderFault O WHERE O.idOrderFault = :pIdOrderFault")
                    .setParameter("pIdOrderFault", pIdOrderFault);
            
            resultDelete = query.executeUpdate();
            
            _transaction.commit();
            
        } catch(HibernateException e) {}
        finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return resultDelete;
    }
}
