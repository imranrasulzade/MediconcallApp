package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.model.dto.request.AdminRegistrationDto;
import com.matrix.mediconcallapp.model.dto.response.AdminDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    UserDto getById(HttpServletRequest request);
    List<UserDto> getAll();

    void changePassword(User user, String newPassword);

    User findByEmail(String email);

    AdminDto addAdmin(AdminRegistrationDto registrationDto);

}
