package com.alex.room.enums;

public enum Periods {
    One(0), Two(1), Three(3), Four(7), Five(14), Six(30), Seven(90);

    private Integer dayAmount;

    Periods(Integer i) {
        dayAmount = i;
    }

    public Integer getDayAmount() {
        return dayAmount;
    }
}
