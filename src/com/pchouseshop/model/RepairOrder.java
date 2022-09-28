package com.pchouseshop.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;

@Entity
@Table(name = "TBL_ORDER")
public class RepairOrder implements Serializable {
    
    @Id
    @GeneratedValue
    @Column(name = "ID_ORDER")
    private int idOrder;
    
    @Column(name = "ID_CUSTOMER")
    private int idCustomer;
    
    @Column(name = "ID_DEVICE")
    private int idDevice;

    @Column(name = "ID_EMPLYOEE")
    private int idEmployee;
    
    @Column(name = "ID_COMPANY")
    private int idCompany;
    
    @Column(name = "TOTAL")
    private double total;
    
    @Column(name = "STATUS")
    private String status;
    
    @Column(name = "CREATED")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date created;
    
    @Column(name = "FINISHED")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date finished;
    
    @Column(name = "PICKED")
    @Temporal(javax.persistence.TemporalType.DATE)
    private Date picked;

    public RepairOrder(int idCustomer, int idDevice, int idEmployee, int idCompany, double total, String status, Date created, Date finished, Date picked) {
        this.idCustomer = idCustomer;
        this.idDevice = idDevice;
        this.idEmployee = idEmployee;
        this.idCompany = idCompany;
        this.total = total;
        this.status = status;
        this.created = created;
        this.finished = finished;
        this.picked = picked;
    }

    public RepairOrder() {
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(int idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(int idDevice) {
        this.idDevice = idDevice;
    }

    public int getIdEmployee() {
        return idEmployee;
    }

    public void setIdEmployee(int idEmployee) {
        this.idEmployee = idEmployee;
    }

    public int getIdCompany() {
        return idCompany;
    }

    public void setIdCompany(int idCompany) {
        this.idCompany = idCompany;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getFinished() {
        return finished;
    }

    public void setFinished(Date finished) {
        this.finished = finished;
    }

    public Date getPicked() {
        return picked;
    }

    public void setPicked(Date picked) {
        this.picked = picked;
    }
}
