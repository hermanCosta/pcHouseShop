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
@Table(name = "TBL_ORDER")
public class OrderModel implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_ORDER")
    private int idOrder;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_CUSTOMER", referencedColumnName = "ID_CUSTOMER")
    private Customer customer;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_DEVICE", referencedColumnName = "ID_DEVICE")
    private Device device;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_EMPLOYEE", referencedColumnName = "ID_EMPLOYEE")
    private Employee employee;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_COMPANY", referencedColumnName = "ID_COMPANY")
    private Company company;
    
    @Column(name = "TOTAL")
    private double total;
    
    @Column(name = "DUE")
    private double due;
    
    @Column(name = "STATUS")
    private String status;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "CREATED")
    private Date created;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "FINISHED")
    private Date finished;
    
    @Temporal(javax.persistence.TemporalType.TIMESTAMP)
    @Column(name = "PICKED")
    private Date picked;
    
    @Column(name = "BAD_SECTOR")
    private int bad_sector;

    @Column(name = "NOTE")
    private String note;
    
    public OrderModel() {
    }

    public OrderModel(Customer customer, Device device, Employee employee, Company company, double total, double due, String status, Date created, Date finished, Date picked, int bad_sector, String note) {
        this.customer = customer;
        this.device = device;
        this.employee = employee;
        this.company = company;
        this.total = total;
        this.due = due;
        this.status = status;
        this.created = created;
        this.finished = finished;
        this.picked = picked;
        this.bad_sector = bad_sector;
        this.note = note;
    }

    public int getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(int idOrder) {
        this.idOrder = idOrder;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Device getDevice() {
        return device;
    }

    public void setDevice(Device device) {
        this.device = device;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public double getDue() {
        return due;
    }

    public void setDue(double due) {
        this.due = due;
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

    public int getBad_sector() {
        return bad_sector;
    }

    public void setBad_sector(int bad_sector) {
        this.bad_sector = bad_sector;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
