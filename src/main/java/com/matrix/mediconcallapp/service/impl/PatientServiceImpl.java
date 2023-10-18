package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.entity.Authority;
import com.matrix.mediconcallapp.enums.AuthorityName;
import com.matrix.mediconcallapp.exception.PatientNotFoundException;
import com.matrix.mediconcallapp.exception.UserAlreadyExistsException;
import com.matrix.mediconcallapp.mapper.PatientMapper;
import com.matrix.mediconcallapp.mapper.UserMapper;
import com.matrix.mediconcallapp.model.dto.response.PatientDto;
import com.matrix.mediconcallapp.model.dto.request.PatientRegistrationRequestDto;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.repository.UserRepository;
import com.matrix.mediconcallapp.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {
    private final UserMapper userMapper;
    private final PatientMapper patientMapper;
    private final UserRepository userRepository;
    private final PatientRepository patientRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public PatientDto add(PatientRegistrationRequestDto requestDto) {
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

            patient.setUser(userRepository.save(user));
            patientRepository.save(patient);

            return patientRepository.findById(patient.getId())
                    .map(patientMapper::toPatientDto)
                    .orElseThrow(PatientNotFoundException::new);
        }
    }

    @Override
    public PatientDto get(Integer id) {
        return patientRepository.findById(id)
                .map(patientMapper::toPatientDto)
                .orElseThrow(PatientNotFoundException::new);
    }
}
