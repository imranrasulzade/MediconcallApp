package com.matrix.mediconcallapp.model.dto.response;

import com.matrix.mediconcallapp.enums.UserStatus;
import lombok.Data;

@Data
public class UserDto {

    private Integer id;

    private String username;
    private String name;
    private String surname;
    private String email;
    private String phone;
    private String birthday;
    private Integer gender;
    private String address;
    private String info;
    private String photoUrl;
    private String role;
    private UserStatus status;
}
