package com.example.georged.orarupb.utils;

/**
 * Created by georgemd on 04.02.2018.
 */

public enum Day {
    MONDAY("Monday", 0),
    TUESDAY("Tuesday", 1),
    WEDNESDAY("Wednesday", 2),
    THURSDAY("Thursday", 3),
    FRIDAY("Friday", 4),
    SATURDAY("Saturday", 5),
    SUNDAY("Sunday", 6);

    private final String name;
    private final int pos;

    Day(String name, int pos) {
        this.name = name;
        this.pos = pos;
    }

    public String getName() {
        return name;
    }

    public int getPos() {
        return pos;
    }

    public static Day valueOf(int pos) {
        switch (pos) {
            case 0:
                return MONDAY;
            case 1:
                return TUESDAY;
            case 2:
                return WEDNESDAY;
            case 3:
                return THURSDAY;
            case 4:
                return FRIDAY;
            case 5:
                return SATURDAY;
            case 6:
                return SUNDAY;
            default:
                return MONDAY;
        }
    }
}
