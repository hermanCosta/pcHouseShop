package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Company;
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

    public List<ProductService> getAllProductsDAO(Company pCompany) {
        try {
            _session = _sessionFactory.openSession();
            
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM ProductService PS WHERE PS.company = :pCompany ORDER BY PS.idProductService ASC")
                    .setParameter("pCompany", pCompany);

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

    public List<ProductService> getMinStockProductDAO(Company pCompany) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM ProductService PS WHERE PS.category = 'PRODUCT' AND PS.qty <= 2 AND PS.company = :pCompany")
                    .setParameter("pCompany", pCompany);

            _listProducts = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
            if (_session != null) {
                _session.close();
            }
        }

        return _listProducts;
    }

    public long addProductServiceDAO(ProductService pProductService) {
        long idProdServAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idProdServAdded = (long) _session.save(pProductService);

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
            Query query = _session.createQuery("FROM ProductService PS "
                    + "WHERE PS.prodServName LIKE :pSearch "
                    + "OR PS.price LIKE :pSearch "
                    + "OR PS.note LIKE :pSearch ORDER BY PS.prodServName")
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

    public List<ProductService> orderSearchProdServDAO(String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM ProductService PS "
                    + "WHERE PS.prodServName LIKE :pSearch "
                    + "OR PS.price LIKE :pSearch "
                    + "OR PS.note LIKE :pSearch ORDER BY PS.prodServName")
                    .setParameter("pSearch", "%" + pSearch + "%")
                    .setMaxResults(5);
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
    
    public ProductService getItemProdServDAO(long pIdProdServ) {
        ProductService itemCustomer = null;
        
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            
            itemCustomer = (ProductService)_session.get(ProductService.class, pIdProdServ);
            
            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }
        
        return itemCustomer;
    }
}
