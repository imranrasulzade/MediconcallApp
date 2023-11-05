package com.matrix.mediconcallapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrix.mediconcallapp.customValidation.ValidUserStatus;
import com.matrix.mediconcallapp.enums.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import jakarta.validation.constraints.Email;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class UserEditReqDto {
    @Schema(hidden = true)
    private Integer id;

    @NotBlank(message = "Username cannot be empty or null")
    @Size(max = 25)
    private String username;

    @Schema(hidden = true)
    private String password;

    @NotBlank(message = "Name cannot be empty or null")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String name;

    @NotBlank(message = "Surname cannot be empty or null")
    @Pattern(regexp = "^[A-Za-z]+$")
    private String surname;

    @Email
    @NotNull
    private String email;

    @Pattern(regexp = "^[0-9]+$")
    @Size(max = 12)
    @NotNull
    private String phone;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    @NotNull
    private Date birthday;

    @Min(0)
    @Max(1)
    @NotNull
    private Integer gender;

    private String address;
    private String info;

    @NotNull
    private MultipartFile photo;

    @ValidUserStatus
    private UserStatus status;
}
