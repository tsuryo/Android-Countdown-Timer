package com.tsuryo.androidcountdown;

public enum TimeUnits {
    YEAR(0), MONTH(1), WEEK(2),//todo: add implementation for y,m,w
    DAY(3), HOUR(4), MINUTE(5),
    SECOND(6);

    private final int mValue;

    TimeUnits(int i) {
        mValue = i;
    }

    public static TimeUnits getInstance() {
        return HOUR;
    }

    public int getValue() {
        return mValue;
    }
}
