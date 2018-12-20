package com.alex.room.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

    private String datePriorRep;
    private String dateNextRep;
    private LocalDate typeDatePriorRep;
    private LocalDate typeDateNextRep;
    private String username;
    private Integer percentFalse;


    public TableInfo() {
    }

    public TableInfo(Integer number, Integer amountElem, Integer amountMistakes, Integer percentFalse, String datePriorRep,
                     String dateNextRep, Integer stage, String username) {

        this.number = number;
        this.amountElem = amountElem;
        this.amountMistakes = amountMistakes;
        this.percentFalse = percentFalse;
        this.datePriorRep = datePriorRep;
        this.dateNextRep = dateNextRep;
        this.stage = stage;
        this.username = username;

        this.typeDatePriorRep = LocalDate.parse(datePriorRep, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
        this.typeDateNextRep = LocalDate.parse(dateNextRep, DateTimeFormatter.ofPattern("dd.MM.yyyy"));
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

    public LocalDate getTypeDatePriorRep() {
        return typeDatePriorRep;
    }

    public void setTypeDatePriorRep(LocalDate typeDatePriorRep) {
        this.typeDatePriorRep = typeDatePriorRep;
    }

    public LocalDate getTypeDateNextRep() {
        return typeDateNextRep;
    }

    public void setTypeDateNextRep(LocalDate typeDateNextRep) {
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
