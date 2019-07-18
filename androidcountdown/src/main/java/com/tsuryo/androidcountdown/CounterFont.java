package com.tsuryo.androidcountdown;

public enum CounterFont {
    DIGITAL(0),
    DIGITAL_BOLD(1),
    DIGITAL_ITALIC(2),
    DIGITAL_ITALIC_BOLD(3),
    REGULAR(4);

    private final Integer mInt;

    CounterFont(Integer i) {
        mInt = i;
    }

    public boolean equalsName(String otherName) {
        return mInt.equals(otherName);
    }

    Integer getValue() {
        return mInt;
    }

    @Override
    public String toString() {
        switch (mInt) {
            case 0:
                return Constants.DIGITAL;
            case 1:
                return Constants.DIGITAL_BOLD;
            case 2:
                return Constants.DIGITAL_ITALIC;
            case 3:
                return Constants.DIGITAL_IT_BOLD;
            default:
                return Constants.DIGITAL;
        }
    }
}
