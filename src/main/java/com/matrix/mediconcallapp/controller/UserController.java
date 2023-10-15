package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/user")
    public UserDto getById(HttpServletRequest request) {
        return userService.getById(request);
    }

    @GetMapping("/")
    public List<UserDto> getAllUser() {
        return userService.getAll();
    }


}
