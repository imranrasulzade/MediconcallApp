package com.matrix.mediconcallapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrix.mediconcallapp.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class PatientRegistrationRequestDto {

    @NotBlank(message = "Username cannot be empty or null")
    private String username;

    @Size(min = 8)
    @Pattern(regexp = "[A-Za-z0-9]+")
    private String password;

    @NotBlank(message = "Name cannot be empty or null")
    private String name;

    @NotBlank(message = "Surname cannot be empty or null")
    private String surname;

    @Email
    private String email;

    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birthday;

    @Min(0)
    @Max(1)
    private Integer gender;

    @NotBlank(message = "Address cannot be empty or null")
    private String address;

    private String info;

    @Schema(hidden = true)
    private final UserStatus status = UserStatus.ACTIVE;

    @NotNull
    private MultipartFile photo;
    @NotNull
    private MultipartFile document;

    private String bankAccount;

}
