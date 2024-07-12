package com.matrix.mediconcallapp.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Data
@Builder
@AllArgsConstructor
public class LoginRes {
    private String username;
    private String token;
    private Set<String> roles;
}
