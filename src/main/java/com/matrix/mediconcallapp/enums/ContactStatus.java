package com.matrix.mediconcallapp.enums;

public enum ContactStatus {
    PENDING(0),
    ACCEPTED(1),
    REMOVED(-1);
    private final int value;
    ContactStatus(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
