package com.alex.room.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    private Integer number;
    private Integer amountElem;
    private Integer percentFalse;
    private String dateNextRep;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getAmountElem() {
        return amountElem;
    }

    public void setAmountElem(Integer amountElem) {
        this.amountElem = amountElem;
    }

    public Integer getPercentFalse() {
        return percentFalse;
    }

    public void setPercentFalse(Integer percentFalse) {
        this.percentFalse = percentFalse;
    }

    public String getDateNextRep() {
        return dateNextRep;
    }

    public void setDateNextRep(String dateNextRep) {
        this.dateNextRep = dateNextRep;
    }
}
