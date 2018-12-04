package com.alex.room.domain;

import com.alex.room.enums.Periods;

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
    private String datePriorRep;
    private String dateNextRep;
    private Integer stage;

    public TableInfo() {
    }

    public TableInfo(Integer number, Integer amountElem, Integer percentFalse, String datePriorRep, String dateNextRep, Integer stage) {
        this.number = number;
        this.amountElem = amountElem;
        this.percentFalse = percentFalse;
        this.datePriorRep = datePriorRep;
        this.dateNextRep = dateNextRep;
        this.stage = stage;
    }

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

    public Integer getStage() {
        return stage;
    }

    public void setStage(Integer stage) {
        this.stage = stage;
    }

    public String getDatePriorRep() {
        return datePriorRep;
    }

    public void setDatePriorRep(String datePriorRep) {
        this.datePriorRep = datePriorRep;
    }
}
