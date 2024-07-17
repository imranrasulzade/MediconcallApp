package com.matrix.mediconcallapp.service.service_interfaces;


import com.matrix.mediconcallapp.model.dto.PageDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorEditReqDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorDto;
import com.matrix.mediconcallapp.model.dto.request.DoctorRegistrationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.DoctorForListProfileDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface DoctorService {
    DoctorDto getById(Integer id);
    DoctorDto getByIdFromHeader(HttpServletRequest request);

    List<DoctorDto> getAll();

    DoctorDto add(DoctorRegistrationRequestDto requestDto);

    PageDto<DoctorForListProfileDto> searchDoctors(Optional<Integer> page,
                                                   Optional<Integer> size,
                                                   Optional<String> name,
                                                   Optional<String> specialty);

    ResponseEntity<?> getDoctorByIdForPatient(HttpServletRequest request, Integer id);

    void update(HttpServletRequest request, DoctorEditReqDto editReqDto);

    List<String> getSpecialties();


}
