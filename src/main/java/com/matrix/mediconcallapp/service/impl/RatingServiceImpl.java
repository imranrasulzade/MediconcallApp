package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.MedicalRecord;
import com.matrix.mediconcallapp.entity.Rating;
import com.matrix.mediconcallapp.exception.child.DoctorNotFoundException;
import com.matrix.mediconcallapp.exception.child.MedicalRecordNotFoundException;
import com.matrix.mediconcallapp.exception.child.RatingAlreadyExistsException;
import com.matrix.mediconcallapp.exception.child.RatingNotFoundException;
import com.matrix.mediconcallapp.mapper.RatingMapper;
import com.matrix.mediconcallapp.model.dto.request.RatingReqDto;
import com.matrix.mediconcallapp.model.dto.response.RatingRespDto;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.MedicalRecordRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.repository.RatingRepository;
import com.matrix.mediconcallapp.service.RatingService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class RatingServiceImpl implements RatingService {
    private final JwtUtil jwtUtil;
    private final RatingRepository ratingRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final RatingMapper ratingMapper;

    @Override
    public void addRating(HttpServletRequest request, RatingReqDto ratingReqDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("rating add method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Doctor doctor = doctorRepository.findById(ratingReqDto.getDoctorId())
                .orElseThrow(DoctorNotFoundException::new);
        List<MedicalRecord> medicalRecords = medicalRecordRepository
                .findByDoctorIdAndPatientIdAndStatus(doctor.getId(), patientId, 1)
                .orElseThrow(MedicalRecordNotFoundException::new);
        int condition = medicalRecords.size();
        Integer checker = ratingRepository
                .countByRaterPatientIdAndRatedDoctorId(patientId, doctor.getId()).orElse(0);
        if(condition > 0){
            if(checker == 0){
                ratingReqDto.setPatientId(patientId);
                ratingReqDto.setTimestamp(LocalDateTime.now());
                Rating rating = ratingMapper.toRating(ratingReqDto);
                ratingRepository.save(rating);
                log.info("Rating added by patientId: {} for doctorId: {}", patientId, doctor.getId());
            } else {
                log.error("Rating already exists by userId: {}", userId);
                throw new RatingAlreadyExistsException();
            }
        }else {
            log.error("rating could not be added due to lack of medical record, userId: {}", userId);
            throw new MedicalRecordNotFoundException();
        }


    }

    @Override
    public List<RatingRespDto> getRating(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return ratingRepository.findByRatedDoctorId(doctorId)
                .orElseThrow(RatingNotFoundException::new)
                .stream().map(ratingMapper::toRatingRespDto).toList();
    }

    @Override
    public List<RatingRespDto> getRatingByDoctorId(Integer id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(DoctorNotFoundException::new);
        return ratingRepository.findByRatedDoctorId(doctor.getId())
                .orElseThrow(RatingNotFoundException::new)
                .stream().map(ratingMapper::toRatingRespDto).toList();
    }


}
