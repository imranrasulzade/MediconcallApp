package com.matrix.mediconcallapp.Annotation;

import com.matrix.mediconcallapp.enums.ReservationStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<ValidReservationStatus, ReservationStatus> {
    @Override
    public boolean isValid(ReservationStatus value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (ReservationStatus validStatus : ReservationStatus.values()) {
            if (validStatus == value) {
                return true;
            }
        }

        return false;
    }
}
