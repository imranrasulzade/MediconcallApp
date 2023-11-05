package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.exception.UserNotFoundException;
import com.matrix.mediconcallapp.exception.parent.NotFoundException;
import com.matrix.mediconcallapp.mapper.UserMapper;
import com.matrix.mediconcallapp.model.dto.request.ChangePasswordDto;
import com.matrix.mediconcallapp.model.dto.request.UserEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.UserStatusDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.repository.UserRepository;
import com.matrix.mediconcallapp.service.UserService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
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

    @Override
    public void updateStatus(UserStatusDto userStatusDto) {
        User user =  userRepository.findById(userStatusDto.getId())
                .orElseThrow(UserNotFoundException::new);
        user.setStatus(userStatusDto.getUserStatus());
        userRepository.save(user);
    }

    @Override
    public List<UserDto> getUserByStatus(UserStatus userStatus) {
         return userRepository.findByStatus(userStatus)
                 .orElseThrow(UserNotFoundException::new)
                 .stream().map(userMapper::toUserDto).toList();
    }

    @Override
    public void updateUser(HttpServletRequest request, UserEditReqDto userEditReqDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        userEditReqDto.setId(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userEditReqDto.setPassword(user.getPassword());
        userRepository.save(userMapper.toUser(userEditReqDto));
    }

    @Override
    public void changePassword(HttpServletRequest request, ChangePasswordDto changePasswordDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if(changePasswordDto.getNewPassword().equals(changePasswordDto.getRetryPassword()) &&
        user.getPassword().equals(passwordEncoder.encode(changePasswordDto.getNewPassword()))){
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
            log.info("Password changed by user_id = {}", userId);
        }
    }

    @Override
    public Resource downloadDocument(String documentPath) {
        try {
            Resource resource = new UrlResource("file:" + documentPath);

            if (resource.exists() && resource.isReadable()) {
                return resource;
            } else {
                throw new NotFoundException("File");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }


}
