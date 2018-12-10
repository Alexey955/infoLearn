package com.alex.room.domain;

public class GroupActivity {
    private String activityName;
    private long userAmount;

    public GroupActivity(String activityName, long userAmount) {
        this.activityName = activityName;
        this.userAmount = userAmount;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public long getUserAmount() {
        return userAmount;
    }

    public void setUserAmount(Integer userAmount) {
        this.userAmount = userAmount;
    }
}
