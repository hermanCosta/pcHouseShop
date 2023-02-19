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
@Table(name = "TBL_ORDER_NOTE")
public class OrderNote implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ORDER_NOTE")
    private Integer idOrderNote;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_ORDER", referencedColumnName = "ID_ORDER")
    private OrderModel order;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    private Employee employee;

    @Column(name = "note")
    private String note;

    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;

    public OrderNote() {
    }

    public OrderNote(OrderModel order, Employee employee, String note, Date created) {
        this.order = order;
        this.employee = employee;
        this.note = note;
        this.created = created;
    }

    public Integer getIdOrderNote() {
        return idOrderNote;
    }

    public void setIdOrderNote(Integer idOrderNote) {
        this.idOrderNote = idOrderNote;
    }

    public OrderModel getOrder() {
        return order;
    }

    public void setOrder(OrderModel order) {
        this.order = order;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
}
