package com.matrix.mediconcallapp.model.dto.response;

import com.matrix.mediconcallapp.model.UserStatus;
import lombok.Data;

import java.util.Date;

@Data
public class UserDto {

    private Integer id;

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
    private Integer isApproved;
    private UserStatus status;
}
