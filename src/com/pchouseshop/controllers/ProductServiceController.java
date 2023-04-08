package com.pchouseshop.controllers;

import com.pchouseshop.dao.ProductServiceDAO;
import com.pchouseshop.model.Company;
import com.pchouseshop.model.ProductService;
import java.util.List;

public class ProductServiceController {
    private final ProductServiceDAO PRODSERV_DAO = new ProductServiceDAO();
    
    public List<ProductService> getAllProductController(Company pCompany) {
        return PRODSERV_DAO.getAllProductsDAO(pCompany);
    }
    
    public List<ProductService> getMinStockProductController(Company pCompany) {
        return PRODSERV_DAO.getMinStockProductDAO(pCompany);       
    }
    
    public long addProductServiceController(ProductService pProductService) {
        return PRODSERV_DAO.addProductServiceDAO(pProductService);
    }
    
    public boolean updateProductService(ProductService pProductService) {
        return PRODSERV_DAO.updateProductServiceDAO(pProductService);
    }
    
    public boolean deleteProductService(ProductService pProductService) {
        return PRODSERV_DAO.deleteProductServiceDAO(pProductService);
    }
    
    public List<ProductService> searchProdServController(String pProdServ) {
        return PRODSERV_DAO.searchProdServDAO(pProdServ);
    }
    
    public List<ProductService> orderSearchProdServController(String pSearch) {
        return  PRODSERV_DAO.orderSearchProdServDAO(pSearch);
    }
    
    public ProductService getItemProdServController(long pIdProdServ) {
        return  PRODSERV_DAO.getItemProdServDAO(pIdProdServ);
    }
}
