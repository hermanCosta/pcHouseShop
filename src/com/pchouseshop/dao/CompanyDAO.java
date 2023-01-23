package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Company;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class CompanyDAO {

    List<Company> _listCompanies = null;
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;

    public List<Company> getAllCompanyDAO() {
        try {
            _session = _sessionFactory.openSession();
            CriteriaBuilder builder = _session.getCriteriaBuilder();
            CriteriaQuery<Company> criteriaQuery = builder.createQuery(Company.class);
            criteriaQuery.from(Company.class);

            _listCompanies = _session.createQuery(criteriaQuery).getResultList();

        } catch (HibernateException e) {
            if (_session != null) {
                _session.close();
            }
        }

        return _listCompanies;
    }

    public Company getCompanyDAO(String name, String password) {
        Company comp = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Company C WHERE C.name = :name AND C.password = :password")
                    .setParameter("name", name)
                    .setParameter("password", password);

            comp = (Company) query.uniqueResult();
            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        return comp;
    }
}
