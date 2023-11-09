package com.matrix.mediconcallapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrix.mediconcallapp.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class DoctorRegistrationRequestDto {
    @NotBlank(message = "Username cannot be empty or null")
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z0-9_.]+$")
    private String username;

    @NotBlank(message = "Username cannot be empty or null")
    @Size(min = 3)
    @Pattern(regexp = "[A-Za-z0-9_.]+")
    private String password;

    @Size(max = 15)
    @NotBlank(message = "Name cannot be empty or null")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String name;

    @Size(max = 25)
    @NotBlank(message = "Surname cannot be empty or null")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String surname;

    @Email
    @NotBlank(message = "email cannot be empty or null")
    @Size(max = 40)
    private String email;

    @NotBlank(message = "phone can not be empty or null")
    @Pattern(regexp = "^[0-9]+$")
    @Size(min = 10, max = 12)
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "birthday can not future date")
    private Date birthday;

    @Min(value = 0, message = "gender value only 0 or 1")
    @Max(value = 1, message = "gender value only 0 or 1")
    @NotNull(message = "gender can not be null")
    private Integer gender;

    @Size(max = 40)
    private String address;
    private String info;

    @Schema(hidden = true)
    private final UserStatus status = UserStatus.INACTIVE;

    @NotNull
    private MultipartFile photo;

    @NotNull
    @Size(max = 45)
    private String specialty;

    @Size(max = 25)
    private String academicTitle;

    @Size(max = 45)
    private String qualification;

    @Size(max = 45)
    private String placeOfWork;

    @NotNull
    @Size(min = 16, max = 16)
    @Pattern(regexp = "^[0-9]+$")
    private String bankAccount;
}
