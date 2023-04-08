package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Company;
import com.pchouseshop.model.Refurb;
import java.util.List;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class RefurbDAO {

    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;
    List<Refurb> _listRefurbProd = null;

    public List<Refurb> getAllRefurbProdDAO(Company pCompany) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Refurb R "
                    + "WHERE R.company = :pCompany "
                    + "ORDER BY R.category ASC")
                    .setParameter("pCompany", pCompany);

            _listRefurbProd = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listRefurbProd;
    }

    public long addRefurbProductDAO(Refurb pRefurbProd) {
        long idRefurbAdded = 0;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            idRefurbAdded = (long) _session.save(pRefurbProd);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idRefurbAdded;
    }

    public Refurb getItemRefurbProdDAO(long pIdRefurbProd) {
        Refurb itemRefurbProd = null;

        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            itemRefurbProd = (Refurb) _session.get(Refurb.class, pIdRefurbProd);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return itemRefurbProd;
    }

    public boolean updateRefurbProdDAO(Refurb pRefurbProd) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();

            _session.update(pRefurbProd);
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public boolean deleteRefurbProdDAO(Refurb pRefurbProd) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            _session.delete(pRefurbProd);

            _transaction.commit();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return true;
    }

    public List<Refurb> searchRefurbDAO(String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Refurb R "
                    + "WHERE R.category LIKE :pSearch "
                    + "OR R.brand LIKE :pSearch "
                    + "OR R.model LIKE :pSearch "
                    + "OR R.price LIKE :pSearch "
                    + "OR R.qty LIKE :pSearch "
                    + "OR R.serialNumber LIKE :pSearch "
                    + "OR R.note LIKE :pSearch "
                    + "OR R.screen LIKE :pSearch "
                    + "OR R.processor LIKE :pSearch "
                    + "OR R.ramMemory LIKE :pSearch "
                    + "OR R.storage LIKE :pSearch "
                    + "OR R.gpuBoard LIKE :pSearch "
                    + "OR R.batteryHealth LIKE :pSearch "
                    + "OR R.custom1 LIKE :pSearch "
                    + "OR R.custom2 LIKE :pSearch "
                    + "OR R.custom3 LIKE :pSearch "
                    + "OR R.custom4 LIKE :pSearch "
                    + "OR R.custom5 LIKE :pSearch "
                    + "OR R.custom6 LIKE :pSearch")
                    .setParameter("pSearch", "%" + pSearch + "%");
            _listRefurbProd = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listRefurbProd;
    }

    public List<Refurb> getAllRefurbByCategoryDAO(Company pCompany, String pCategory) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Refurb R "
                    + "WHERE R.company = :pCompany "
                    + "AND R.category = :category "
                    + "ORDER BY R.brand ASC")
                    .setParameter("pCompany", pCompany)
                    .setParameter("category", pCategory);

            _listRefurbProd = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listRefurbProd;
    }

    public List<Refurb> searchRefurbByCategoryDAO(Company pCompany, String pCategory, String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Refurb R "
                    + "WHERE R.company = :pCompany "
                    + "AND R.category = :pCategory "
                    + "AND(R.brand LIKE :pSearch "
                    + "OR R.model LIKE :pSearch "
                    + "OR R.price LIKE :pSearch "
                    + "OR R.qty LIKE :pSearch "
                    + "OR R.serialNumber LIKE :pSearch "
                    + "OR R.note LIKE :pSearch "
                    + "OR R.screen LIKE :pSearch "
                    + "OR R.processor LIKE :pSearch "
                    + "OR R.ramMemory LIKE :pSearch "
                    + "OR R.storage LIKE :pSearch "
                    + "OR R.gpuBoard LIKE :pSearch "
                    + "OR R.batteryHealth LIKE :pSearch "
                    + "OR R.custom1 LIKE :pSearch "
                    + "OR R.custom2 LIKE :pSearch "
                    + "OR R.custom3 LIKE :pSearch "
                    + "OR R.custom4 LIKE :pSearch "
                    + "OR R.custom5 LIKE :pSearch "
                    + "OR R.custom6 LIKE :pSearch) "
                    + "ORDER BY R.brand ASC")
                    .setParameter("company", pCompany)
                    .setParameter("pCategory", pCategory)
                    .setParameter("pSearch", "%" + pSearch + "%");
            _listRefurbProd = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listRefurbProd;
    }

    public List<Refurb> getAllCustomRefurbProdDAO(Company pCompany) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Refurb R "
                    + "WHERE R.company = :pCompany "
                    + "AND R.category <> 'COMPUTER' "
                    + "AND R.category <> 'CONSOLE' "
                    + "AND R.category <> 'MONITOR' "
                    + "AND R.category <> 'TV' "
                    + "ORDER BY R.category ASC")
                    .setParameter("pCompany", pCompany);

            _listRefurbProd = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listRefurbProd;
    }

    public List<Refurb> searchCustomRefurbDAO(Company pCompany, String pSearch) {
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Refurb R "
                    + "WHERE R.company = :company "
                    + "AND R.category <> 'COMPUTER' "
                    + "AND R.category <> 'CONSOLE' "
                    + "AND R.category <> 'MONITOR' "
                    + "AND R.category <> 'TV' "
                    + "AND (R.brand LIKE :pSearch "
                    + "OR R.model LIKE :pSearch "
                    + "OR R.price LIKE :pSearch "
                    + "OR R.qty LIKE :pSearch "
                    + "OR R.serialNumber LIKE :pSearch "
                    + "OR R.note LIKE :pSearch "
                    + "OR R.screen LIKE :pSearch "
                    + "OR R.processor LIKE :pSearch "
                    + "OR R.ramMemory LIKE :pSearch "
                    + "OR R.storage LIKE :pSearch "
                    + "OR R.gpuBoard LIKE :pSearch "
                    + "OR R.batteryHealth LIKE :pSearch "
                    + "OR R.custom1 LIKE :pSearch "
                    + "OR R.custom2 LIKE :pSearch "
                    + "OR R.custom3 LIKE :pSearch "
                    + "OR R.custom4 LIKE :pSearch "
                    + "OR R.custom5 LIKE :pSearch "
                    + "OR R.custom6 LIKE :pSearch) "
                    + "ORDER BY R.category ASC")
                    .setParameter("company", pCompany)
                    .setParameter("pSearch", "%" + pSearch + "%");
            _listRefurbProd = query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listRefurbProd;
    }

}
