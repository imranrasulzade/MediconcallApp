package com.matrix.mediconcallapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrix.mediconcallapp.Annotation.ImageFile;
import com.matrix.mediconcallapp.model.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class DoctorRegistrationRequestDto {
    @NotBlank(message = "Username cannot be empty or null")
    @Size(max = 25)
    private String username;

    @Size(min = 8)
    @Pattern(regexp = "[A-Za-z0-9]+")
    private String password;

    @NotBlank(message = "Name cannot be empty or null")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String name;

    @NotBlank(message = "Surname cannot be empty or null")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String surname;

    @Email
    private String email;

    @Pattern(regexp = "^[0-9]+$")
    @Size(max = 12)
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birthday;

    @Min(0)
    @Max(1)
    private Integer gender;
    private String address;
    private String info;

    @Schema(hidden = true)
    private final Integer isApproved = 0;

    @Schema(hidden = true)
    private final UserStatus status = UserStatus.ACTIVE;

    private MultipartFile photo;

    private String specialty;
    private String academicTitle;
    private String qualification;
    private String placeOfWork;
    private String bankAccount;
}
