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
@Table(name = "TBL_CUSTOMER")
public class Customer implements Serializable {
   
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_CUSTOMER")
    private long idCustomer;
    
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PERSON", referencedColumnName = "ID_PERSON")
    private Person person;
    
    @OneToOne()
    @JoinColumn(name = "ID_COMPANY", referencedColumnName = "ID_COMPANY")
    private Company company;

    public Customer() {
    }

    public Customer(Person person, Company company) {
        this.person = person;
        this.company = company;
    }

    public long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
