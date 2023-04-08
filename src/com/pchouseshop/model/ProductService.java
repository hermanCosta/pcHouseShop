package com.pchouseshop.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_PROD_SERV")
public class ProductService implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID_PROD_SERV")
    private long idProductService;
    
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
        
    @OneToOne()
    @JoinColumn(name = "ID_COMPANY", referencedColumnName = "ID_COMPANY")
    private Company company;

    public ProductService() {
        
    }

    public ProductService(String prodServName, int qty, double price, String category, String note, Company company) {
        this.prodServName = prodServName;
        this.qty = qty;
        this.price = price;
        this.category = category;
        this.note = note;
        this.company = company;
    }

    public long getIdProductService() {
        return idProductService;
    }
    
    public void setIdProductService(long idProductService) {
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

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return prodServName;
    }   
}
