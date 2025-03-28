package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Fault;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class FaultDAO {
    List<Fault> _listFault = new ArrayList<>();
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    
    public List<Fault> getAllFaultDAO() {
        try {
            _session = _sessionFactory.openSession();
            CriteriaBuilder builder = _session.getCriteriaBuilder();
            CriteriaQuery<Fault> criteriaQuery = builder.createQuery(Fault.class);

            criteriaQuery.from(Fault.class);
            _listFault = _session.createQuery(criteriaQuery).getResultList();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listFault;
    }
    
    public int addFaultDAO(Fault pFault) {
        Integer idFaultAdded = 0;
        
        try {
            _session = _sessionFactory.openSession();    
            _transaction = _session.beginTransaction();
            idFaultAdded = (Integer)_session.save(pFault);
            
            _transaction.commit();
            
        } catch (HibernateException e) {
        } finally {
            if (_session != null) 
                _session.close();           
        }   
        
        return idFaultAdded;           
    }
    
    public boolean updateFaultDAO(Fault pFault) {        
        try {
            _session = _sessionFactory.openSession();    
            _transaction = _session.beginTransaction();
            _session.update(pFault);
            
            _transaction.commit();
            
        } catch (HibernateException e) {
        } finally {
            if (_session != null) 
                _session.close();              
        }   
        
        return true;        
    }
    
    public boolean deleteFaultDAO(Fault pFault) {        
        try {
            _session = _sessionFactory.openSession();    
            _transaction = _session.beginTransaction();
            _session.delete(pFault);
            
            _transaction.commit();
            
        } catch (HibernateException e) {
        } finally {
            if (_session != null) 
                _session.close();              
        }   
        
        return true;        
    }
    
    public List<Fault> searchFaultDAO(String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Fault F WHERE F.description LIKE :pSearch")
                    .setParameter("pSearch", "%"+ pSearch +"%");

            _listFault = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listFault;
    }
    
    public Fault getItemFaultDAO(int pIdFault) {
        Fault itemFault = null;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            
            itemFault = (Fault)_session.get(Fault.class, pIdFault);
            
            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return itemFault;
    }
}
