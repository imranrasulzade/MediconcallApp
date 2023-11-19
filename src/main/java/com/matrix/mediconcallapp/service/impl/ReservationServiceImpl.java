package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.exception.child.*;
import com.matrix.mediconcallapp.mapper.ReservationMapper;
import com.matrix.mediconcallapp.model.dto.request.ReservationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.ReservationStatusDto;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import com.matrix.mediconcallapp.repository.ContactRepository;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.repository.ReservationRepository;
import com.matrix.mediconcallapp.service.ReservationService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import com.matrix.mediconcallapp.service.utility.TimeUtility;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final DoctorRepository doctorRepository;
    private final ReservationMapper reservationMapper;
    private final PatientRepository patientRepository;
    private final ContactRepository contactRepository;
    private final JwtUtil jwtUtil;

    @Override
    public List<TimeDto> getAllTimes(HttpServletRequest request, Integer doctorId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();

        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(DoctorNotFoundException::new);
        boolean condition = contactRepository.findAcceptedContact(doctorId, patientId).isPresent();
        if(condition){
            List<Reservation> reservations = reservationRepository.findByDoctorId(doctorId)
                    .orElse(Collections.emptyList());
            List<TimeDto> checkedList = new ArrayList<>();
            List<TimeDto> timeDtoList = TimeUtility.getAllTimes();
            if(!reservations.isEmpty()){
                for(TimeDto t : timeDtoList){
                    for(Reservation reservation : reservations){
                        if(reservation.getDate().getDayOfMonth() == t.getDay() &&
                                reservation.getDate().getHour() == t.getHour()){
                            t.setStatus(1);
                            t.setDoctorId(reservation.getDoctor().getId());
                            t.setDoctorName(reservation.getDoctor().getUser().getName());
                            break;
                        }else {
                            t.setStatus(0);
                            t.setDoctorId(reservation.getDoctor().getId());
                            t.setDoctorName(reservation.getDoctor().getUser().getName());
                        }
                    }
                    checkedList.add(t);
                }
            }else {
                for(TimeDto t : timeDtoList){
                    t.setStatus(1);
                    t.setDoctorId(doctor.getId());
                    t.setDoctorName(doctor.getUser().getName());
                }
                return timeDtoList;
            }
            return checkedList;
        }else{
            log.error("times could not be given because contact could not be found, userId: {}", userId);
            throw new ContactNotFoundException();
        }
    }
    @Override
    public List<ReservationDto> getAllReservations() throws ReservationNotFoundException {
        return reservationRepository.findAll()
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Override
    public List<ReservationDto> getReservationsOfDoctor(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return reservationRepository.findByDoctorId(doctorId)
                .orElseThrow(ReservationNotFoundException::new)
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Override
    public List<ReservationDto> getByPatient(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        return reservationRepository.findByPatientId(patientId)
                .orElseThrow(ReservationNotFoundException::new)
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Transactional
    @Override
    public void add(HttpServletRequest request, ReservationRequestDto requestDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("reservation add method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = doctorRepository.findById(requestDto.getDoctorId())
                .orElseThrow(DoctorNotFoundException::new);
        requestDto.setStatus(ReservationStatus.PENDING);
        checkDateTimeValidity(requestDto.getDate());
        checkReservation(doctor.getId(), requestDto);
        Reservation reservation = reservationMapper.toReservation(requestDto);
        reservation.setDoctor(doctor);
        reservation.setPatient(patient);
        reservationRepository.save(reservation);
        log.info("Reservation added by patientId: {} with doctorId: {}. userId: {}", patientId, doctor.getId(), userId);
    }

    @Override
    public List<ReservationDto> getByStatus(HttpServletRequest request, ReservationStatus status) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return reservationRepository.findByStatusAndDoctorId(status, doctorId)
                .orElseThrow(ReservationNotFoundException::new)
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Override
    public void changeStatus(HttpServletRequest request, ReservationStatusDto reservationStatusDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("reservation changeStatus method started by userId: {}", userId);
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Reservation reservation = reservationRepository.findById(reservationStatusDto.getId())
                .orElseThrow(ReservationNotFoundException::new);
        if(reservation.getDoctor().getId().equals(doctorId)){
            reservation.setStatus(reservationStatusDto.getStatus());
        }else{
            throw new ReservationNotFoundException();
        }
        reservationRepository.save(reservation);
        log.info("Reservation (reservationId: {}) status changed by userId: {}", reservation.getId(),  userId);
    }

    @Override
    public void cancel(HttpServletRequest request, Integer id) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("reservation cancel method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
        if(reservation.getPatient().getId().equals(patientId)){
            reservation.setStatus(ReservationStatus.CANCELLED);
        }else {
            throw new ReservationNotFoundException();
        }
        reservationRepository.save(reservation);
        log.info("Reservation (reservationId: {}) cancelled by userId: {}", reservation.getId(),  userId);
    }




    private void checkReservation(Integer id, ReservationRequestDto requestDto){
        List<Reservation> checkList = reservationRepository.findByDoctorId(id)
                .orElse(Collections.emptyList());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for(Reservation check: checkList){
            String strDate = check.getDate().format(formatter);
            if(requestDto.getDate().equals(strDate)){
                throw new ReservationAlreadyExistsException();
            }
        }
    }

    private void checkDateTimeValidity(String strDateTime) {
        try{
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime inputDateTime = LocalDateTime.parse(strDateTime, formatter);
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime oneWeekLater = now.plusWeeks(1);
            if (inputDateTime.isBefore(now.plusDays(1)) || inputDateTime.isAfter(oneWeekLater)) {
                log.error("Error due to DateTimeRange");
                throw new DateTimeRangeException();
            }
            int hour = inputDateTime.getHour();
            if (hour < 9 || (hour == 13) || hour >= 18) {
                log.error("Error due to DateTimeRange");
                throw new DateTimeRangeException();
            }
        }catch (DateTimeParseException e){
            log.error("Error due to: {} ", e.getMessage());
            throw new InvalidDateTimeFormatException();
        }
    }

}

