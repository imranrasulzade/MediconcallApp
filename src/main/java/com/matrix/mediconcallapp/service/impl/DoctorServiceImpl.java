package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.AuthorityName;
import com.matrix.mediconcallapp.exception.DoctorNotFoundException;
import com.matrix.mediconcallapp.exception.UserAlreadyExistsException;
import com.matrix.mediconcallapp.mapper.DoctorMapper;
import com.matrix.mediconcallapp.mapper.UserMapper;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorForListProfileDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorProfileDto;
import com.matrix.mediconcallapp.model.dto.response.SimpleDoctorProfileDto;
import com.matrix.mediconcallapp.repository.*;
import com.matrix.mediconcallapp.service.DoctorService;
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
        DoctorDto doctorDto = doctorRepository.findById(id)
                .map(doctorMapper::toDoctorDto)
                .orElseThrow(DoctorNotFoundException::new);
        doctorDto.setAvgRating(ratingRepository.findAverageRatingByDoctorId(id).orElse(0d));
        return doctorDto;
    }

    @Override
    public DoctorDto getByIdFromHeader(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer id = doctorRepository.findDoctorByUserId(userId).getId();
        DoctorDto doctorDto = doctorRepository.findById(id)
                .map(doctorMapper::toDoctorDto)
                .orElseThrow(DoctorNotFoundException::new);
        doctorDto.setAvgRating(ratingRepository.findAverageRatingByDoctorId(id).orElse(0d));
        return doctorDto;
    }

    @Override
    public List<DoctorDto> getAll() {
        List<Doctor> doctors = doctorRepository.findAll();
        List<DoctorDto> doctorDtos = new ArrayList<>();

        for (Doctor doctor : doctors) {
            DoctorDto doctorDto = doctorMapper.toDoctorDto(doctor);
            Double avgRating = ratingRepository.findAverageRatingByDoctorId(doctor.getId()).orElse(0d);
            doctorDto.setAvgRating(avgRating);
            doctorDtos.add(doctorDto);
        }

        return doctorDtos;
    }

    @Transactional
    @Override
    public DoctorDto add(DoctorRegistrationRequestDto requestDto) {
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

            return doctorRepository.findById(doctor.getId())
                    .map(doctorMapper::toDoctorDto)
                    .orElseThrow(DoctorNotFoundException::new);
        }
    }

    @Override
    public List<DoctorForListProfileDto> getDoctorByName(String name) {
        return userRepository.findDoctorsByNameLike(name)
                .orElseThrow(DoctorNotFoundException::new).stream()
                .map(userMapper::toDoctorForListProfileDto)
                .toList();
    }

    @Override
    public ResponseEntity<?> getDoctorByIdForPatient(HttpServletRequest request, Integer doctorId) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(DoctorNotFoundException::new);
        Integer contactCondition = contactRepository
                .countByDoctorIdAndPatientIdAndStatus(doctorId, patientId, 1)
                .orElse(0);
        Double avgRating = ratingRepository.findAverageRatingByDoctorId(doctorId).orElse(0d);
        if(contactCondition > 0){
            DoctorProfileDto doctorProfileDto = doctorMapper.toDoctorProfileDto(doctor);
            doctorProfileDto.setAvgRating(avgRating);
            return ResponseEntity.ok(doctorProfileDto);
        }else {
            SimpleDoctorProfileDto simpleDoctorProfileDto = doctorMapper.toSimpleDoctorProfileDto(doctor);
            simpleDoctorProfileDto.setAvgRating(avgRating);
            return ResponseEntity.ok(simpleDoctorProfileDto);
        }
    }
}
