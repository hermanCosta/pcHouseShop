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
@Table(name = "TBL_ORDER_FAULT")
public class OrderFault implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ORDER_FAULT")
    private Integer idOrderFault;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID_ORDER")
    private OrderModel order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_FAULT", referencedColumnName = "ID_FAULT")
    private Fault fault;

    public OrderFault() {
    }

    public OrderFault(OrderModel order, Fault fault) {
        this.order = order;
        this.fault = fault;
    }

    public Integer getIdOrderFault() {
        return idOrderFault;
    }

    public void setIdOrderFault(Integer idOrderFault) {
        this.idOrderFault = idOrderFault;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public Fault getFault() {
        return fault;
    }

    public void setFault(Fault fault) {
        this.fault = fault;
    }
}
