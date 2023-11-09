package com.matrix.mediconcallapp.enums;

import lombok.Getter;

@Getter
public enum ContactStatus {
    PENDING(0),
    ACCEPTED(1),
    REMOVED(-1);
    private final int value;
    ContactStatus(int value) {
        this.value = value;
    }

}
