package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Company;
import com.pchouseshop.model.OrderModel;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class OrderDAO {
    
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    List<OrderModel> _listOrder = null;
    
    public long getLastOrderIdDAO() {
        long orderId = 0;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("SELECT MAX(O.idOrder) FROM OrderModel O");
            
            orderId = (long) query.uniqueResult();
            _transaction.commit();
            
            if (orderId == 0) {
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
    
    public long addOrderDAO(OrderModel pOrder) {
        long idOrderAdded = 0;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idOrderAdded = (long) _session.save(pOrder);
            
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return idOrderAdded;
    }
    
    public OrderModel getItemOrderDAO(long pIdOrder) {
        OrderModel itemOrder = null;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            
            itemOrder = (OrderModel) _session.get(OrderModel.class, pIdOrder);
            
            _transaction.commit();
            
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return itemOrder;
    }
    
    public List<OrderModel> getAllOrderDAO(Company pCompany) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM OrderModel O WHERE O.company = :pCompany ORDER BY O.created DESC")
                    .setParameter("pCompany", pCompany);
            
            _listOrder = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return _listOrder;
    }
    
    public List<OrderModel> searchOrderDAO(Company pCompany, String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("SELECT DISTINCT O FROM OrderModel O, Customer C, Person P, Device D "
                    + "WHERE O.company = :pCompany "
                    + "AND ((O.customer = C AND C.person = P AND P.firstName LIKE :pSearch) "
                    + "OR (O.customer = C AND C.person = P AND P.lastName LIKE :pSearch) "
                    + "OR (O.customer = C AND C.person = P AND P.contactNo LIKE :pSearch) "
                    + "OR (O.customer = C AND C.person = P AND P.email LIKE :pSearch) "
                    + "OR (O.device = D AND D.brand LIKE :pSearch) "
                    + "OR (O.device = D AND D.model LIKE :pSearch) "
                    + "OR (O.device = D AND D.serialNumber LIKE :pSearch)"
                    + "OR O.idOrder LIKE :pSearch)")
                    .setParameter("pCompany", pCompany)
                    .setParameter("pSearch", "%" + pSearch + "%");
            _listOrder = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return _listOrder;
    }
    
    public boolean updateOrderDAO(OrderModel pOrderModel) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            _session.update(pOrderModel);
            
            _transaction.commit();
            
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return true;
    }
    
//    public long updateTotalDueDAO(double pTotal, double pDue, Company pCompany, long pIdOrder) {
//        long idOrder = 0;
//        try {
//            _session = _sessionFactory.openSession();
//            _transaction = _session.beginTransaction();
//            Query query = _session.createQuery("UPDATE OrderModel O SET O.total = ?, O.due = :pDue WHERE O.company = :pCompany AND O.idOrder = :pIdOrder")
//                    .setParameter("pTotal", pTotal)
//                    .setParameter("pDue", pDue)
//                    .setParameter("pCompany", pCompany)
//                    .setParameter("pIdOrder", pIdOrder);
//            
//            idOrder = query.executeUpdate();
//            _transaction.commit();
//            
//        } catch (HibernateException e) {
//        } finally {
//            if (_session != null) {
//                _session.close();
//            }
//        }
//        
//        return idOrder;
//    }
}
