package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.AuthorityName;
import com.matrix.mediconcallapp.exception.child.DoctorNotFoundException;
import com.matrix.mediconcallapp.exception.child.UserAlreadyExistsException;
import com.matrix.mediconcallapp.mapper.DoctorMapper;
import com.matrix.mediconcallapp.mapper.UserMapper;
import com.matrix.mediconcallapp.model.dto.request.DoctorEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorForListProfileDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorProfileDto;
import com.matrix.mediconcallapp.model.dto.response.SimpleDoctorProfileDto;
import com.matrix.mediconcallapp.repository.*;
import com.matrix.mediconcallapp.service.service_interfaces.DoctorService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class DoctorServiceImpl implements DoctorService {
    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;
    private final DoctorMapper doctorMapper;
    private final UserMapper userMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final RatingRepository ratingRepository;
    private final PatientRepository patientRepository;
    private final ContactRepository contactRepository;
    private final JwtUtil jwtUtil;
    @Override
    public DoctorDto getById(Integer id) {
        log.info("Doctor getById method started for {}", id);
        DoctorDto doctorDto = doctorRepository.findById(id)
                .map(doctorMapper::toDoctorDto)
                .orElseThrow(DoctorNotFoundException::new);
        doctorDto.setAvgRating(ratingRepository.findAverageRatingByDoctorId(id).orElse(0d));
        log.info("Doctor getById method done {}", id);
        return doctorDto;
    }

    @Override
    public DoctorDto getByIdFromHeader(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("doctor getByIdFromHeader method started by userId: {}", userId);
        Integer id = doctorRepository.findDoctorByUserId(userId).getId();
        DoctorDto doctorDto = doctorRepository.findById(id)
                .map(doctorMapper::toDoctorDto)
                .orElseThrow(DoctorNotFoundException::new);
        doctorDto.setAvgRating(ratingRepository.findAverageRatingByDoctorId(id).orElse(0d));
        log.info("doctor getByIdFromHeader method done by userId: {}", userId);
        return doctorDto;
    }

    @Override
    public List<DoctorDto> getAll() {
        log.info("doctor getAll method started");
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorDto> doctorDtos = new ArrayList<>();

        for (Doctor doctor : doctors) {
            DoctorDto doctorDto = doctorMapper.toDoctorDto(doctor);
            Double avgRating = ratingRepository.findAverageRatingByDoctorId(doctor.getId()).orElse(0d);
            doctorDto.setAvgRating(avgRating);
            doctorDtos.add(doctorDto);
        }
        log.info("doctor getAll method done");
        return doctorDtos;
    }

    @Transactional
    @Override
    public DoctorDto add(DoctorRegistrationRequestDto requestDto) {
        log.info("doctor add method started by {}", requestDto.getUsername());
        if(userRepository.findByUsername(requestDto.getUsername()).isPresent() ||
                userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        } else {
            requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            Doctor doctor = doctorMapper.toDoctorForAdd(requestDto);
            User user = userMapper.toUserForAddDoctor(requestDto);
            Authority roles = new Authority(AuthorityName.DOCTOR.name());
            Set<Authority> authorities = new HashSet<>();
            authorities.add(roles);
            user.setAuthorities(authorities);

            doctor.setUser(user);
            user.setDoctor(doctor);
            userRepository.save(user);
            DoctorDto doctorDto = doctorRepository.findById(doctor.getId())
                    .map(doctorMapper::toDoctorDto)
                    .orElseThrow(DoctorNotFoundException::new);
            log.info("user registered as a doctor role, username: {}", user.getUsername());
            return doctorDto;
        }
    }

    @Override
    public List<DoctorForListProfileDto> getDoctorByName(String name) {
        log.info("doctor getDoctorByName method started for {}", name);
        List<DoctorForListProfileDto> doctorForListProfileDtoList = userRepository
                .findDoctorsByNameLike(name)
                .orElseThrow(DoctorNotFoundException::new).stream()
                .map(userMapper::toDoctorForListProfileDto)
                .toList();
        log.info("doctor getDoctorByName method done for {}", name);
        return doctorForListProfileDtoList;
    }

    @Override
    public ResponseEntity<?> getDoctorByIdForPatient(HttpServletRequest request, Integer doctorId) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("doctor getDoctorByIdForPatient method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(DoctorNotFoundException::new);
        Integer contactCondition = contactRepository
                .countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 1)
                .orElse(0);
        Double avgRating = ratingRepository.findAverageRatingByDoctorId(doctorId).orElse(0d);
        if(contactCondition > 0){
            DoctorProfileDto doctorProfileDto = doctorMapper.toDoctorProfileDto(doctor);
            doctorProfileDto.setAvgRating(avgRating);
            log.info("doctor getDoctorByIdForPatient method done by userId: {}", userId);
            return ResponseEntity.ok(doctorProfileDto);
        }else {
            SimpleDoctorProfileDto simpleDoctorProfileDto = doctorMapper.toSimpleDoctorProfileDto(doctor);
            simpleDoctorProfileDto.setAvgRating(avgRating);
            log.info("doctor getDoctorByIdForPatient method done by userId: {}", userId);
            return ResponseEntity.ok(simpleDoctorProfileDto);
        }
    }

    @Override
    public void update(HttpServletRequest request, DoctorEditReqDto editReqDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("doctor update method started by userId: {}", userId);
        Doctor doctor = doctorRepository.findByUserId(userId)
                .orElseThrow(DoctorNotFoundException::new);
        editReqDto.setId(doctor.getId());
        editReqDto.setUserId(userId);
        doctorRepository.save(doctorMapper.toDoctor(editReqDto));
        log.info("Doctor record updated by userId: {}", userId);
    }
}
