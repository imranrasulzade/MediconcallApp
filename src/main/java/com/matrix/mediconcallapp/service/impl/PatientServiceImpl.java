package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.enums.AuthorityName;
import com.matrix.mediconcallapp.exception.child.PatientNotFoundException;
import com.matrix.mediconcallapp.exception.child.UserAlreadyExistsException;
import com.matrix.mediconcallapp.mapper.PatientMapper;
import com.matrix.mediconcallapp.mapper.UserMapper;
import com.matrix.mediconcallapp.model.dto.request.PatientEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.repository.UserRepository;
import com.matrix.mediconcallapp.service.PatientService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final UserMapper userMapper;
    private final PatientMapper patientMapper;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    @Transactional
    @Override
    public PatientDto add(PatientRegistrationRequestDto requestDto) {
        log.info("add method start {}", requestDto.getUsername());
        if(userRepository.findByUsername(requestDto.getUsername()).isPresent() ||
                userRepository.findByEmail(requestDto.getEmail()).isPresent()){
            throw new UserAlreadyExistsException();
        } else {
            requestDto.setPassword(passwordEncoder.encode(requestDto.getPassword()));
            Patient patient = patientMapper.toPatientForAdd(requestDto);
            User user = userMapper.toUserForAddPatient(requestDto);
            Authority roles = new Authority(AuthorityName.PATIENT.name());
            Set<Authority> authorities = new HashSet<>();
            authorities.add(roles);
            user.setAuthorities(authorities);

            patient.setUser(user);
            user.setPatient(patient);
            userRepository.save(user);
            PatientDto patientDto = patientRepository.findById(patient.getId())
                    .map(patientMapper::toPatientDto)
                    .orElseThrow(PatientNotFoundException::new);

            log.info("users registered as patients, username: {}", user.getUsername());
            return patientDto;
        }
    }

    @Override
    public PatientDto get(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        return patientRepository.findById(patientId)
                .map(patientMapper::toPatientDto)
                .orElseThrow(PatientNotFoundException::new);
    }

    @Override
    public PatientDto getById(Integer patientId) {
        return patientRepository.findById(patientId)
                .map(patientMapper::toPatientDto)
                .orElseThrow(PatientNotFoundException::new);
    }

    @Override
    public void update(HttpServletRequest request, PatientEditReqDto editReqDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Patient patient = patientRepository.findByUserId(userId)
                .orElseThrow(PatientNotFoundException::new);
        editReqDto.setId(patient.getId());
        editReqDto.setUserId(userId);
        patientRepository.save(patientMapper.toPatient(editReqDto));
        log.info("Patient record updated by userId: {}", userId);
    }
}
