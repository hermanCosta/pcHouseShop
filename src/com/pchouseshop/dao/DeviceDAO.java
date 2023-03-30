package com.pchouseshop.dao;

import com.pchouseshop.connection.HibernateUtil;
import com.pchouseshop.model.Device;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

public class DeviceDAO {

    List<Device> _listDevice = new ArrayList<>();
    Session _session = null;
    SessionFactory _sessionFactory = HibernateUtil.getSessionFactory();
    Transaction _transaction;

    public List<Device> getAllDeviceDAO() {
        try {
            _session = _sessionFactory.openSession();
            CriteriaBuilder builder = _session.getCriteriaBuilder();
            CriteriaQuery<Device> criteriaQuery = builder.createQuery(Device.class);

            criteriaQuery.from(Device.class);
            _listDevice = _session.createQuery(criteriaQuery).getResultList();

        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return _listDevice;
    }

    public int addDeviceDAO(Device pDevice) {
        int idDeviceAdded = 0;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            idDeviceAdded = (int) _session.save(pDevice);

            _transaction.commit();

        } catch (Exception e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return idDeviceAdded;
    }

    public Device searchDeviceBySerialNumber(String pSearch) {
        Device deviceItem = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("FROM Device D WHERE D.serialNumber = :pSearch")
                    .setParameter("pSearch", pSearch);

            deviceItem = (Device) query.uniqueResult();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return deviceItem;
    }

    public ArrayList<String> searchBrandDAO(String pSearch) {
        ArrayList<String> listBrand = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("SELECT DISTINCT D.brand FROM Device D WHERE D.brand LIKE :pSearch")
                    .setParameter("pSearch", "%" + pSearch + "%");

            listBrand = (ArrayList<String>) query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return listBrand;
    }

    public ArrayList<String> searchModelDAO(String pBrand, String pModel) {
        ArrayList<String> listModel = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("SELECT DISTINCT D.model FROM Device D WHERE D.brand = :pBrand AND D.model LIKE :pModel")
                    .setParameter("pBrand", pBrand)
                    .setParameter("pModel", "%" + pModel + "%");

            listModel = (ArrayList<String>) query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return listModel;
    }

    public ArrayList<String> searchSerialNumberDAO(String pBrand, String pSerialNumber) {
        ArrayList<String> listSerialNumber = null;
        try {
            _session = _sessionFactory.openSession();
            _transaction = _session.beginTransaction();
            Query query = _session.createQuery("SELECT DISTINCT D.serialNumber FROM Device D WHERE D.brand = :pBrand AND D.serialNumber LIKE :pSerialNumber")
                    .setParameter("pBrand", pBrand)
                    .setParameter("pSerialNumber", "%" + pSerialNumber + "%");

            listSerialNumber = (ArrayList<String>) query.getResultList();
            _transaction.commit();
        } catch (HibernateException e) {
        } finally {
            if (_session != null) {
                _session.close();
            }
        }

        return listSerialNumber;
    }
}
