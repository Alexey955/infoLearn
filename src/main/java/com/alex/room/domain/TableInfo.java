package com.alex.room.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Entity
public class TableInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Min(value = 1, message = "Less than 1.")
    @Max(value = 1000, message = "More than 1000.")
    private Integer number;

    @Min(value = 1, message = "Less than 1.")
    @Max(value = 1000, message = "More than 1000.")
    private Integer amountElem;

    @Min(value = 0, message = "Less than 0.")
    private Integer amountMistakes;

    @Min(value = 1, message = "Less than 1.")
    @Max(value = 7, message = "More than 7.")
    private Integer stage;

    private Integer percentFalse;
    private String datePriorRep;
    private String dateNextRep;
    private Date typeDatePriorRep;
    private Date typeDateNextRep;
    private String username;



    public TableInfo() {
    }

    public TableInfo(Integer number, Integer amountElem, Integer amountMistakes, Integer percentFalse, String datePriorRep,
                     String dateNextRep, Integer stage, String username) throws ParseException {

        this.number = number;
        this.amountElem = amountElem;
        this.amountMistakes = amountMistakes;
        this.percentFalse = percentFalse;
        this.datePriorRep = datePriorRep;
        this.dateNextRep = dateNextRep;
        this.stage = stage;
        this.username = username;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd.MM.yyyy");

        this.typeDatePriorRep = simpleDateFormat.parse(datePriorRep);

        this.typeDateNextRep = simpleDateFormat.parse(dateNextRep);
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

    public Integer getAmountMistakes() {
        return amountMistakes;
    }

    public void setAmountMistakes(Integer amountMistakes) {
        this.amountMistakes = amountMistakes;
    }
}
