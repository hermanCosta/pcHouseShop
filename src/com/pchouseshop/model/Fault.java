package com.pchouseshop.model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "TBL_FAULT")
public class Fault implements Serializable {

    @Id
    @GeneratedValue
    @Column(name = "ID_FAULT")
    private long idFault;
    
    @Column(name = "DESCRIPTION")
    private String description;

    public Fault() {
    }

    public Fault(String description) {
        this.description = description;
    }

    public long getIdFault() {
        return idFault;
    }

    public void setIdFault(long idFault) {
        this.idFault = idFault;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return description;
    }
    
    
}
