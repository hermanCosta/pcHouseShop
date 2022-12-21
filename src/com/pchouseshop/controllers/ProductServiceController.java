package com.pchouseshop.controllers;

import com.pchouseshop.dao.ProductServiceDAO;
import com.pchouseshop.model.ProductService;
import java.util.List;

/**
 *
 * @author herman
 */
public class ProductServiceController {
    private final ProductServiceDAO PRODSERV_DAO = new ProductServiceDAO();
    
    public List<ProductService> getAllProductController(int pIdCompany) {
        return PRODSERV_DAO.getAllProductsDAO(pIdCompany);
    }
    
    public List<ProductService> getMinStockProductController(int pIdCompany) {
        return PRODSERV_DAO.getMinStockProductDAO(pIdCompany);       
    }
    
    public int addProductServiceController(ProductService pProductService) {
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
    
    public ProductService getItemProdServController(int pIdProdServ) {
        return  PRODSERV_DAO.getItemProdServDAO(pIdProdServ);
    }
}
