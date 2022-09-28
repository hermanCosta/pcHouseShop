package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.ProductService;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class ProductServiceDAO {

    List<ProductService> _listProducts = null;
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;

    public List<ProductService> getAllProductsDAO(int pIdCompany) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM ProductService PS WHERE PS.idCompany = :idCompany ORDER BY PS.idProductService ASC")
                    .setParameter("idCompany", pIdCompany);

            _listProducts = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listProducts;
    }

    public List<ProductService> getMinStockProductDAO(int pIdCompany) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM ProductService PS WHERE PS.category = 'PRODUCT' AND PS.qty <= 2 AND PS.idCompany = :idCompany")
                    .setParameter("idCompany", pIdCompany);

            _listProducts = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
            if (_session != null) {
                _session.close();
            }
        }

        return _listProducts;
    }

    public int addProductServiceDAO(ProductService pProductService) {
        Integer idProdServAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idProdServAdded = (Integer) _session.save(pProductService);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idProdServAdded;
    }

    public boolean updateProductServiceDAO(ProductService pProductService) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            _session.update(pProductService);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public boolean deleteProductServiceDAO(ProductService pProductService) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            _session.delete(pProductService);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public List<ProductService> searchProdServDAO(String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM ProductService PS WHERE PS.prodServName LIKE :pSearch OR PS.price LIKE :pSearch OR PS.note LIKE :pSearch")
                    .setParameter("pSearch", "%" + pSearch + "%");
            _listProducts = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listProducts;
    }
}
