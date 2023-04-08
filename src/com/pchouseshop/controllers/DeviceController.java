package com.pchouseshop.controllers;

import com.pchouseshop.dao.DeviceDAO;
import com.pchouseshop.model.Device;
import java.util.ArrayList;
import java.util.List;

public class DeviceController {

    private final DeviceDAO DEVICE_DAO = new DeviceDAO();

    public List<Device> getAllDeviceDAO() {
        return DEVICE_DAO.getAllDeviceDAO();
    }

    public long addDeviceController(Device pDevice) {
        return DEVICE_DAO.addDeviceDAO(pDevice);
    }

    public List<Device> getAllDeviceController() {
        return DEVICE_DAO.getAllDeviceDAO();
    }

    public Device searchDeviceBySerialNumberController(String pSearch) {
        return DEVICE_DAO.searchDeviceBySerialNumber(pSearch);
    }

    public ArrayList<String> searchBrandController(String pSearch) {
        return DEVICE_DAO.searchBrandDAO(pSearch);
    }

    public ArrayList<String> searchModelController(String pModel, String pBrand) {
        return DEVICE_DAO.searchModelDAO(pModel, pBrand);
    }

    public ArrayList<String> searchSerialNumberController(String pBrand, String pSerialNumber) {
        return DEVICE_DAO.searchSerialNumberDAO(pBrand, pSerialNumber);
    }
}
