package com.alex.room.enums;

public enum Periods {
    ONE(0), TWO(1), THREE(3), FOUR(7), FIVE(14), SIX(30), SEVEN(90);

    private Integer dayAmount;

    Periods(Integer i) {
        dayAmount = i;
    }

    public Integer getDayAmount() {
        return dayAmount;
    }
}
