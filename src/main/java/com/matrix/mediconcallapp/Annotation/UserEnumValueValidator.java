package com.matrix.mediconcallapp.Annotation;


import com.matrix.mediconcallapp.enums.UserStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UserEnumValueValidator implements ConstraintValidator<ValidUserStatus, UserStatus> {
    @Override
    public boolean isValid(UserStatus value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }

        for (UserStatus validStatus : UserStatus.values()) {
            if (validStatus == value) {
                return true;
            }
        }
        return false;
    }
}