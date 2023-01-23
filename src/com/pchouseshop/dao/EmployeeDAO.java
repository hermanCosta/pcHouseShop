package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Employee;
import com.pchouseshop.model.Person;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class EmployeeDAO {

    List<Employee> _listEmployee = new ArrayList<>();
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;

    public List<Employee> getAllEmployeeDAO() {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            CriteriaBuilder builder = _session.getCriteriaBuilder();
            CriteriaQuery<Employee> criteriaQuery = builder.createQuery(Employee.class);

            criteriaQuery.from(Employee.class);
            _listEmployee = _session.createQuery(criteriaQuery).getResultList();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listEmployee;
    }

    public int addEmployeeDAO(Employee pEmployee) {
        Integer idEmployeeAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            idEmployeeAdded = (Integer) _session.save(pEmployee);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idEmployeeAdded;
    }

    public boolean updateEmployeeDAO(Employee pEmplyoee) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            if (_session.contains(pEmplyoee)) {
                _session.update(pEmplyoee);
                _transaction.commit();

            }
            _session.update(pEmplyoee);
            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public boolean deleteEmplyoeeDAO(int pEmployeeId) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Employee cust = _session.find(Employee.class, pEmployeeId);

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

    public Employee getEmployeeDAO(Person pPerson) {
        Employee employee = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Employee E WHERE E.person = :person")
                    .setParameter("person", pPerson);

            employee = (Employee) query.uniqueResult();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        return employee;
    }
    
    public Employee getItemEmployeeDAO(int pIdEmployeeId) {
        Employee itemEmployee = null;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            
            itemEmployee = (Employee)_session.get(Employee.class, pIdEmployeeId);
            
            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return itemEmployee;
    }
    
    public Employee getEmployeeByPassDAO(String pPassStr) {
         Employee employee = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Employee E WHERE E.password = :password")
                    .setParameter("password", pPassStr);

            employee = (Employee) query.uniqueResult();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        return employee;
    }
}
