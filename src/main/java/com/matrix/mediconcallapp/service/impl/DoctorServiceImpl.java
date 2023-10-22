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
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.UserRepository;
import com.matrix.mediconcallapp.service.DoctorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Override
    public DoctorDto getById(Integer id) {
        return doctorRepository.findById(id)
                .map(doctorMapper::toDoctorDto)
                .orElseThrow(DoctorNotFoundException::new);
    }

    @Override
    public List<DoctorDto> getAll() {
        return doctorRepository.findAll()
                .stream()
                .map(doctorMapper::toDoctorDto)
                .toList();
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

            //doctor.setUser(userRepository.save(user));
            //doctorRepository.save(doctor);

            doctor.setUser(user);
            user.setDoctor(doctor);
            userRepository.save(user);

            return doctorRepository.findById(doctor.getId())
                    .map(doctorMapper::toDoctorDto)
                    .orElseThrow(DoctorNotFoundException::new);
        }
    }
}
