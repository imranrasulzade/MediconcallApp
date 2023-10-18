package com.matrix.mediconcallapp.model.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Past;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;


import java.util.Date;

@Data
public class AdminDto {
    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private Date birthday;
    private Integer gender;
    private String address;
    private String info;
    private String photoUrl;
}
