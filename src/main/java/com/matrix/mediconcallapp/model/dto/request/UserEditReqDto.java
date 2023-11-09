package com.matrix.mediconcallapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrix.mediconcallapp.customValidation.annotation.ValidUserStatus;
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
    @Size(max = 50)
    @Pattern(regexp = "[A-Za-z0-9_.]+$")
    private String username;

    @Schema(hidden = true)
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
    @Size(min = 12, max = 15)
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

    @NotNull
    private MultipartFile photo;

    @ValidUserStatus(message = "user status can be ACTIVE, INACTIVE or DELETED")
    private UserStatus status;
}
