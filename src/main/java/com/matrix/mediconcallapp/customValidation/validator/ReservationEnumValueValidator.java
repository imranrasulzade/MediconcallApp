package com.matrix.mediconcallapp.customValidation.validator;

import com.matrix.mediconcallapp.customValidation.annotation.ValidReservationStatus;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ReservationEnumValueValidator implements ConstraintValidator<ValidReservationStatus, ReservationStatus> {
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
