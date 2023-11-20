package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.model.dto.request.ChangePasswordDto;
import com.matrix.mediconcallapp.model.dto.request.UserEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.UserStatusDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public UserDto getById(HttpServletRequest request) {
        return userService.getById(request);
    }


    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void updateUser(HttpServletRequest request,
                                           @ModelAttribute @Valid UserEditReqDto userEditReqDto){
        userService.updateUser(request, userEditReqDto);
    }

    @PatchMapping("/password")
    public void changePassword(HttpServletRequest request,
                                               @RequestBody @Valid ChangePasswordDto changePasswordDto){
        userService.changePassword(request, changePasswordDto);
    }


    @GetMapping("/admin/view-status")
    public List<UserDto> getUserByStatus(@RequestParam UserStatus userStatus){
        return userService.getUserByStatus(userStatus);
    }



    @PatchMapping("/admin/status")
    public void updateStatus(@Valid @RequestBody UserStatusDto userStatusDto){
        userService.updateStatus(userStatusDto);
    }


    @GetMapping("/admin/users")
    public List<UserDto> getAllUser() {
        return userService.getAll();
    }
}
