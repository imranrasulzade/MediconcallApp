package com.matrix.mediconcallapp.model.dto.request;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class LoginReq {
    private String username;
    private String password;
}
