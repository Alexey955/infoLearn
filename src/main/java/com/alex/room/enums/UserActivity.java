package com.alex.room.enums;

public enum  UserActivity {
    ACTIVE(7), NORMAL(30), PASSIVE(90);

    private Integer amountDays;

    UserActivity(Integer i) {
        this.amountDays = i;
    }

    public Integer getAmountDays() {
        return amountDays;
    }
}
