package com.matrix.mediconcallapp.Annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ImageFileValidator.class)
@Documented
public @interface ImageFile {
    String message() default "Only image files are accepted.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
