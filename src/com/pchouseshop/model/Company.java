package com.pchouseshop.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author herman
 */

@Entity(name = "TBL_COMPANY")
@Table(name = "TBL_COMPANY")
public class Company implements Serializable {
    @Id
    @GeneratedValue
    @Column(name = "ID_COMPANY")
    private int idCompany;
    
    @Column(name = "NAME") 
    private String name;
    
    @Column(name = "ADDRESS")
    private String address;
    
    @Column(name = "CONTACT_ONE")
    private String contactOne;
    
    @Column(name = "CONTACT_TWO")
    private String contactTwo;
    
    @Column(name = "EMAIL")
    private String email;
    
    @Column(name = "PASSWORD")
    private String password;
    
    public Company(){
        
    }

    public Company(int idCompany, String name, String address, String contactOne, String contactTwo, String email, String password) {
        this.idCompany = idCompany;
        this.name = name;
        this.address = address;
        this.contactOne = contactOne;
        this.contactTwo = contactTwo;
        this.email = email;
        this.password = password;
    }

    public int getIdCompany() {
        return idCompany;
    }
               
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContactOne() {
        return contactOne;
    }

    public void setContactOne(String contactOne) {
        this.contactOne = contactOne;
    }

    public String getContactTwo() {
        return contactTwo;
    }

    public void setContactTwo(String contactTwo) {
        this.contactTwo = contactTwo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Company{" + "idCompany=" + idCompany + ", name=" + name + ", address=" + address + ", contactOne=" + contactOne + ", contactTwo=" + contactTwo + ", email=" + email + ", password=" + password + '}';
    }

    
}
