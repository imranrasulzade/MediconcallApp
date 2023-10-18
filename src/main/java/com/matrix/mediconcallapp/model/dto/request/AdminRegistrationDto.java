package com.matrix.mediconcallapp.model.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.matrix.mediconcallapp.model.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Data
public class AdminRegistrationDto {
    private String username;
    private String password;
    private String name;
    private String surname;
    private String email;
    private String phone;

    @NotBlank(message = "User birthday cannot be null")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Past
    private Date birthday;
    private Integer gender;
    private String address;
    private String info;
    private MultipartFile photo;

    @Enumerated(EnumType.STRING)
    @Schema(hidden = true)
    private UserStatus status = UserStatus.ACTIVE;
}
