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
@Table(name = "TBL_EMPLOYEE")
public class Employee implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_EMPLOYEE")
    private int idEmployee;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ID_PERSON", referencedColumnName = "ID_PERSON")
    private Person person;

    @Column(name = "USERNAME")
    private String username;
    
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "ACCESS_LEVEL")
    private String accessLevel;

    public Employee() {
    }

    public Employee(Person person, String username, String accessLevel) {
        this.person = person;
        this.username = username;
        this.accessLevel = accessLevel;
    }

    public int getIdEmplyoee() {
        return idEmployee;
    }

    public void setIdEmplyoee(int idEmplyoee) {
        this.idEmployee = idEmplyoee;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }
}
