package com.matrix.mediconcallapp.customValidation.validator;

import com.matrix.mediconcallapp.customValidation.annotation.ImageFile;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;

public class ImageFileValidator implements ConstraintValidator<ImageFile, MultipartFile> {
    private static final List<String> SUPPORTED_IMAGE_TYPES = Arrays.asList("image/jpeg", "image/jpg", "image/png");

    @Override
    public void initialize(ImageFile constraintAnnotation) {
    }

    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file != null) {
            String fileName = file.getOriginalFilename().toLowerCase();
            return SUPPORTED_IMAGE_TYPES.contains(file.getContentType()) ||
                    fileName.endsWith(".jpg") ||
                    fileName.endsWith(".jpeg") ||
                    fileName.endsWith(".png");
        } else
            return false;
    }
}
