package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import com.matrix.mediconcallapp.exception.child.PasswordWrongException;
import com.matrix.mediconcallapp.exception.child.UserNotFoundException;
import com.matrix.mediconcallapp.mapper.UserMapper;
import com.matrix.mediconcallapp.model.dto.request.ChangePasswordDto;
import com.matrix.mediconcallapp.model.dto.request.UserEditReqDto;
import com.matrix.mediconcallapp.model.dto.request.UserStatusDto;
import com.matrix.mediconcallapp.model.dto.response.UserDto;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.UserRepository;
import com.matrix.mediconcallapp.service.service_interfaces.UserService;
import com.matrix.mediconcallapp.service.utility.FilePathProcessor;
import com.matrix.mediconcallapp.service.utility.ImageToBase64;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final JwtUtil jwtUtil;
    private final BCryptPasswordEncoder passwordEncoder;
    private final DoctorRepository doctorRepository;

    @Override
    public UserDto getById(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("user getById method started by userId: {}", userId);
        UserDto userDto = userRepository.findById(userId)
                .map(userMapper::toUserDto)
                .orElseThrow(UserNotFoundException::new);
//        String base64ImageUrl = ImageToBase64.convertLocalImageToBase64(userDto.getPhotoUrl());
//        userDto.setPhotoUrl(base64ImageUrl);
        String processedPath = FilePathProcessor.processFilePath(userDto.getPhotoUrl());
        userDto.setPhotoUrl(processedPath);
        log.info("user getById method done by userId: {}", userId);
        return userDto;
    }

    @Override
    public List<UserDto> getAll() {
        log.info("user getAll method started");
        List<UserDto> userDtoList = userRepository.findAll()
                .stream()
                .map(userMapper::toUserDto)
                .toList();
        userDtoList.forEach(it-> it.setPhotoUrl(FilePathProcessor.processFilePath(it.getPhotoUrl())));
        log.info("user getAll method done");
        return userDtoList;
    }


    @Override
    @Transactional
    public void updateStatus(UserStatusDto userStatusDto) {
        log.info("user updateStatus method start");
        User user =  userRepository.findById(userStatusDto.getId())
                .orElseThrow(UserNotFoundException::new);
        if(user.getDoctor() != null){
            Doctor doctor = user.getDoctor();
            doctor.setStatus(convertStatusToInteger(userStatusDto.getUserStatus()));
            doctorRepository.save(doctor);
        }
        if(user.getDoctor() == null && user.getPatient() == null){
            user.setStatus(UserStatus.ACTIVE);
        }else{
            user.setStatus(userStatusDto.getUserStatus());
        }
        userRepository.save(user);
        log.info("User (userId: {}) status updated by admin", user.getId());
    }

    @Override
    public List<UserDto> getUserByStatus(UserStatus userStatus) {
        log.info("user getUserByStatus method started for userStatus: {}", userStatus.name());
        List<UserDto> userDtoList = userRepository.findByStatus(userStatus)
                .orElseThrow(UserNotFoundException::new)
                .stream().map(userMapper::toUserDto).toList();
        log.info("user getUserByStatus method done for userStatus: {}", userStatus.name());
         return userDtoList;
    }

    @Override
    public void updateUser(HttpServletRequest request, UserEditReqDto userEditReqDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("user updateUser method started by userId: {}", userId);
        userEditReqDto.setId(userId);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userEditReqDto.setPassword(user.getPassword());
        userRepository.save(userMapper.toUser(userEditReqDto));
        log.info("User record updated by userId: {}", userId);
    }

    @Override
    public void changePassword(HttpServletRequest request, ChangePasswordDto changePasswordDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("user changePassword method started by userId: {}", userId);
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if(changePasswordDto.getNewPassword().equals(changePasswordDto.getRetryPassword()) &&
         passwordEncoder.matches(changePasswordDto.getCurrentPassword(), user.getPassword())){
            user.setPassword(passwordEncoder.encode(changePasswordDto.getNewPassword()));
            userRepository.save(user);
            log.info("Password changed by user_id = {}", userId);
        }else {
            log.error("failed to change password");
            throw new PasswordWrongException();
        }
    }


    public void changePassword(User user, String newPassword) {
        user.setPassword(newPassword);
        userRepository.save(user);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(UserNotFoundException::new);
    }

    private Integer convertStatusToInteger(UserStatus status){
        int s;
        if(status == UserStatus.ACTIVE){
            s = 1;
        }else if(status == UserStatus.INACTIVE){
            s = 0;
        }else {
            s = -1;
        }
        return s;
    }


}
