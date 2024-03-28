package com.matrix.mediconcallapp.service.service_interfaces;

import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.model.dto.request.ChangePasswordDto;
import com.matrix.mediconcallapp.model.dto.request.UserEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.UserStatusDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface UserService {
    UserDto getById(HttpServletRequest request);
    List<UserDto> getAll();

    void changePassword(User user, String newPassword);

    User findByEmail(String email);

    void updateStatus(UserStatusDto statusDto);

    List<UserDto> getUserByStatus(UserStatus userStatus);

    void updateUser(HttpServletRequest request, UserEditReqDto userEditReqDto);

    void changePassword(HttpServletRequest request, ChangePasswordDto changePasswordDto);


}
