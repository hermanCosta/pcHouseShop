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
@Table(name = "TBL_DEPOSIT")
public class Deposit implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_DEPOSIT")
    private int idDeposit;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID_ORDER")
    private OrderModel order;

    @Column(name = "AMOUNT")
    private double amount;

    public Deposit() {
    }

    public Deposit(OrderModel idOrder, double amount) {
        this.order = idOrder;
        this.amount = amount;
    }

    public int getIdDeposit() {
        return idDeposit;
    }

    public void setIdDeposit(int idDeposit) {
        this.idDeposit = idDeposit;
    }

    public OrderModel getIdOrder() {
        return order;
    }

    public void setIdOrder(OrderModel idOrder) {
        this.order = idOrder;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }
}
