package com.matrix.mediconcallapp.customValidation.annotation;

import com.matrix.mediconcallapp.customValidation.validator.UserEnumValueValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UserEnumValueValidator.class)
public @interface ValidUserStatus {
    String message() default "Undefined User Status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
