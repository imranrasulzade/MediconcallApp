package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.ChangePasswordDto;
import com.matrix.mediconcallapp.model.dto.request.UserEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/info")
    public ResponseEntity<UserDto> getById(HttpServletRequest request) {
        return ResponseEntity.ok(userService.getById(request));
    }



    @PutMapping(value = "/edit", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Void> updateUser(HttpServletRequest request,
                                           @ModelAttribute UserEditReqDto userEditReqDto){
        userService.updateUser(request, userEditReqDto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> changePassword(HttpServletRequest request,
                                               @RequestBody ChangePasswordDto changePasswordDto){
        userService.changePassword(request, changePasswordDto);
        return ResponseEntity.ok().build();
    }


}
