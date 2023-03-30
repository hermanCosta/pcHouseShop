package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Company;
import com.pchouseshop.model.Customer;
import com.pchouseshop.model.Person;
import java.util.ArrayList;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CustomerDAO {

    List<Customer> _listCustomer = new ArrayList<>();
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;

    public List<Customer> getAllCustomerDAO(Company pCompany) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Customer C WHERE C.company = :company ORDER BY C.person ASC")
                    .setParameter("company", pCompany);

            _listCustomer = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listCustomer;
    }

    public long addCustomerDAO(Customer pCustomer) {
        long idCustomerAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            idCustomerAdded = (long) _session.save(pCustomer);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idCustomerAdded;
    }

    public boolean updateCustomerDAO(Customer pCustomer) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            if (_session.contains(pCustomer)) {
                _session.update(pCustomer);
                _transaction.commit();

            }
            _session.update(pCustomer);
            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public boolean deleteCustomerDAO(long pId) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Customer cust = _session.find(Customer.class, pId);

            _session.delete(cust);

            _transaction.commit();

        } catch (HibernateException e) {
            if (_session.getTransaction() != null) {
                _session.getTransaction().rollback();
            }
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public Customer getCustomerDAO(Person pPerson) {
        Customer customer = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Customer C WHERE C.person = :person")
                    .setParameter("person", pPerson);

            customer = (Customer) query.uniqueResult();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        return customer;
    }
    
    public Customer getItemCustomerDAO(long pIdCustomer) {
        Customer itemCustomer = null;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            
            itemCustomer = (Customer)_session.get(Customer.class, pIdCustomer);
            
            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
            
        }
        
        return itemCustomer;
    }
    
    public Customer searchCustomerByContactNoDAO(String pContactNo) {
        Customer customer = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Customer C WHERE C.person.idPerson = (SELECT DISTINCT P.idPerson FROM Person P WHERE P.contactNo = :pContactNo)")
                    .setParameter("pContactNo", pContactNo);

            customer = (Customer) query.uniqueResult();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        return customer;
    }
}
