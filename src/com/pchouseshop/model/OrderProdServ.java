package com.pchouseshop.model;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_ORDER_PROD_SERV")
public class OrderProdServ implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ORDER_PROD_SERV")
    private Integer idOrderProdServ;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID_ORDER")
    private OrderModel order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PROD_SERV", referencedColumnName = "ID_PROD_SERV")
    private ProductService prodServ;

    @Column(name = "QTY")
    private int qty;

    @Column(name = "TOTAL")
    private Double total;

    public OrderProdServ() {
    }

    public OrderProdServ(OrderModel order, ProductService prodServ, int qty, Double total) {
        this.order = order;
        this.prodServ = prodServ;
        this.qty = qty;
        this.total = total;
    }

    public Integer getIdOrderProdServ() {
        return idOrderProdServ;
    }

    public void setIdOrderProdServ(Integer idOrderProdServ) {
        this.idOrderProdServ = idOrderProdServ;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public ProductService getProdServ() {
        return prodServ;
    }

    public void setProdServ(ProductService prodServ) {
        this.prodServ = prodServ;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }
}
