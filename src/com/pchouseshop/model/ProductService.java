package com.pchouseshop.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PROD_SERV")
public class ProductService implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID_PROD_SERV")
    private int idProductService;
    
    @Column(name = "NAME")
    private String prodServName;
    
    @Column(name = "QTY")
    private int qty;
        
    @Column(name = "PRICE")
    private double price;
    
    @Column(name = "CATEGORY")
    private String category;
    
    @Column(name = "NOTE")
    private String note;
        
    @Column(name = "ID_COMPANY")
    private int idCompany;

    public ProductService() {
        
    }

    public ProductService(String prodServName, int qty, double price, String category, String note, int idCompany) {
        this.prodServName = prodServName;
        this.qty = qty;
        this.price = price;
        this.category = category;
        this.note = note;
        this.idCompany = idCompany;
    }

    public int getIdProductService() {
        return idProductService;
    }
    
    public void setIdProductService(int idProductService) {
        this.idProductService = idProductService;
    }

    public String getProdServName() {
        return prodServName;
    }

    public void setProdServName(String prodServName) {
        this.prodServName = prodServName;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    @Override
    public String toString() {
        return "ProductService{" + "idProductService=" + idProductService + ", prodServName=" + prodServName + ", qty=" + qty + ", price=" + price + ", category=" + category + ", note=" + note + ", idCompany=" + idCompany + '}';
    }   
}
