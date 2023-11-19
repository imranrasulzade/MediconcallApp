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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserDto> getById(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getById(request));
    }


    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser(HttpServletRequest request,
                                           @ModelAttribute @Valid UserEditReqDto userEditReqDto){
        userService.updateUser(request, userEditReqDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(HttpServletRequest request,
                                               @RequestBody @Valid ChangePasswordDto changePasswordDto){
        userService.changePassword(request, changePasswordDto);
        return ResponseEntity.ok().build();
    }


    @GetMapping("/admin/view-status")
    public ResponseEntity<List<UserDto>> getUserByStatus(@RequestParam UserStatus userStatus){
        return ResponseEntity.ok(userService.getUserByStatus(userStatus));
    }



    @PatchMapping("/admin/status")
    public ResponseEntity<Void> updateStatus(@Valid @RequestBody UserStatusDto userStatusDto){
        userService.updateStatus(userStatusDto);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


    @GetMapping("/admin/users")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAll());
    }
}
