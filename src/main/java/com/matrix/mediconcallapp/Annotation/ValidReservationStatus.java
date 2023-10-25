package com.matrix.mediconcallapp.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
public @interface ValidReservationStatus {
    String message() default "Undefined Reservation Status";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
