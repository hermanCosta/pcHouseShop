package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
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

public class PersonDAO {

    List<Person> _listPerson = new ArrayList<>();
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;

    public List<Person> getAllPersonDAO() {
        try {
            _session = _sessionFactory.openSession();
            CriteriaBuilder builder = _session.getCriteriaBuilder();
            CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);

            criteriaQuery.from(Person.class);
            _listPerson = _session.createQuery(criteriaQuery).getResultList();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listPerson;
    }

    public int addPersonDAO(Person pPerson) {
        Integer idPersonAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idPersonAdded = (Integer) _session.save(pPerson);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idPersonAdded;
    }

    public boolean updatePersonDAO(Person pPerson) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            _session.update(pPerson);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public boolean deletePersonDAO(Person pPerson) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            _session.delete(pPerson);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public List<Person> searchPersonDAO(String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Person P "
                    + "WHERE P.firstName "
                    + "LIKE :pSearch "
                    + "OR P.lastName "
                    + "LIKE :pSearch "
                    + "OR P.contactNo "
                    + "LIKE :pSearch "
                    + "OR P.email "
                    + "LIKE :pSearch")
                    .setParameter("pSearch", "%"+ pSearch +"%");

            _listPerson = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listPerson;
    }
    
    public Person searchPesonByContactNoDAO(String pContactNo) {
        Person person = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Person P "
                    + "WHERE P.contactNo = :pContactNo")
                    .setParameter("pContactNo", pContactNo);

            person = (Person) query.uniqueResult();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return person;
    }
}
