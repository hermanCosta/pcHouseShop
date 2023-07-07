package com.pchouseshop.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "TBL_ORDER_PAYMENT")
public class OrderPayment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ORDER_PAYMENT")
    private long IdOrderPayment;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID_ORDER")
    private OrderModel order;

    @Column(name = "PAY_METHOD")
    private String payMethod;

    @Column(name = "AMOUNT_DUED")
    private double amountDued;

    @Column(name = "AMOUNT_PAID")
    private double amountPaid;

    @Column(name = "CHANGE_DUED")
    private double changeDued;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "DT_TRANSACTION")
    private Date dtTransaction;

    public OrderPayment() {
    }

    public OrderPayment(OrderModel order, String payMethod, double amountDued, double amountPaid, double changeDued, Date dtTransaction) {
        this.order = order;
        this.payMethod = payMethod;
        this.amountDued = amountDued;
        this.amountPaid = amountPaid;
        this.changeDued = changeDued;
        this.dtTransaction = dtTransaction;
    }

    public long getIdOrderPayment() {
        return IdOrderPayment;
    }

    public void setIdOrderPayment(long IdOrderPayment) {
        this.IdOrderPayment = IdOrderPayment;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public String getPayMethod() {
        return payMethod;
    }

    public void setPayMethod(String payMethod) {
        this.payMethod = payMethod;
    }

    public double getAmountDued() {
        return amountDued;
    }

    public void setAmountDued(double amountDued) {
        this.amountDued = amountDued;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public void setAmountPaid(double amountPaid) {
        this.amountPaid = amountPaid;
    }

    public double getChangeDued() {
        return changeDued;
    }

    public void setChangeDued(double changeDued) {
        this.changeDued = changeDued;
    }

    public Date getDtTransaction() {
        return dtTransaction;
    }

    public void setDtTransaction(Date dtTransaction) {
        this.dtTransaction = dtTransaction;
    }
}
