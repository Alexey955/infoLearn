package com.alex.room.domain;

public class Stats {
    private int stage;
    private double avg;
    private long count;

    public Stats(int stage, double avg, long count) {
        this.stage = stage;
        this.avg = avg;
        this.count = count;
    }

    public int getStage() {
        return stage;
    }

    public double getAvg() {
        return avg;
    }

    public long getCount() {
        return count;
    }
}
