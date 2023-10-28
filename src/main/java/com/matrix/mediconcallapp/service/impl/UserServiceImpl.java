package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.AuthorityName;
import com.matrix.mediconcallapp.exception.UserAlreadyExistsException;
import com.matrix.mediconcallapp.exception.UserNotFoundException;
import com.matrix.mediconcallapp.mapper.UserMapper;
import com.matrix.mediconcallapp.model.dto.response.AdminDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.repository.UserRepository;
import com.matrix.mediconcallapp.service.UserService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    @Override
    public UserDto getById(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        return userRepository.findById(userId)
                .map(userMapper::toUserDto)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public List<UserDto> getAll() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
    }

    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }



}
