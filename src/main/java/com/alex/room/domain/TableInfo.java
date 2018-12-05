package com.alex.room.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

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
    private Date typeDatePriorRep;
    private Date typeDateNextRep;
    private Integer stage;
    private String username;

    public TableInfo() {
    }

    public TableInfo(Integer number, Integer amountElem, Integer percentFalse, String datePriorRep,
                     String dateNextRep, Integer stage, String username) throws ParseException {

        this.number = number;
        this.amountElem = amountElem;
        this.percentFalse = percentFalse;
        this.datePriorRep = datePriorRep;
        this.dateNextRep = dateNextRep;
        this.stage = stage;
        this.username = username;

        Date dateForConver;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        dateForConver = simpleDateFormat.parse(datePriorRep);
        this.typeDatePriorRep = dateForConver;

        dateForConver = simpleDateFormat.parse(dateNextRep);
        this.typeDateNextRep = dateForConver;

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

    public Date getTypeDatePriorRep() {
        return typeDatePriorRep;
    }

    public void setTypeDatePriorRep(Date typeDatePriorRep) {
        this.typeDatePriorRep = typeDatePriorRep;
    }

    public Date getTypeDateNextRep() {
        return typeDateNextRep;
    }

    public void setTypeDateNextRep(Date typeDateNextRep) {
        this.typeDateNextRep = typeDateNextRep;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String user) {
        this.username = user;
    }
}
