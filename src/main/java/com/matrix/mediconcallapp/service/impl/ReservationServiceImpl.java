package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.enums.ReservationStatus;
import com.matrix.mediconcallapp.exception.*;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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

        boolean condition = contactRepository.findAcceptedContact(doctorId, patientId).isPresent();
        if(condition){
            List<Reservation> reservations = reservationRepository.findByDoctorId(doctorId)
                    .orElse(Collections.emptyList());
            List<TimeDto> checkedList = new ArrayList<>();
            if(!reservations.isEmpty()){
                List<TimeDto> timeDtoList = TimeUtility.getAllTimes();
                for(TimeDto t : timeDtoList){
                    for(Reservation reservation : reservations){
                        if(reservation.getDate().getDayOfMonth() == t.getDay() &&
                                reservation.getDate().getHour() == t.getHour()){
                            t.setStatus(1);
                            break;
                        }else {
                            t.setStatus(0);
                        }
                    }
                    checkedList.add(t);
                }
            }else {
                throw new ReservationNotFoundException();
            }
            return checkedList;
        }else{
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
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Patient patient = new Patient();
        patient.setId(patientId);
        Doctor doctor = doctorRepository.findById(requestDto.getDoctorId())
                .orElseThrow(DoctorNotFoundException::new);
        requestDto.setStatus(ReservationStatus.PENDING);
        //check reservation
        checkDateTimeValidity(requestDto.getDate());
        checkReservation(doctor.getId(), requestDto);
        Reservation reservation = reservationMapper.toReservation(requestDto);
        reservation.setDoctor(doctor);
        reservation.setPatient(patient);
        reservationRepository.save(reservation);
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
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Reservation reservation = reservationRepository.findById(reservationStatusDto.getId())
                .orElseThrow(ReservationNotFoundException::new);
        if(reservation.getDoctor().getId().equals(doctorId)){
            reservation.setStatus(reservationStatusDto.getStatus());
        }else{
            throw new ReservationNotFoundException();
        }
        reservationRepository.save(reservation);
    }

    @Override
    public void cancel(HttpServletRequest request, Integer id) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Reservation reservation = reservationRepository.findById(id)
                .orElseThrow(ReservationNotFoundException::new);
        if(reservation.getPatient().getId().equals(patientId)){
            reservation.setStatus(ReservationStatus.CANCELLED);
        }else {
            throw new ReservationNotFoundException();
        }
        reservationRepository.save(reservation);
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
                throw new DateTimeRangeException();
            }
            int hour = inputDateTime.getHour();
            if (hour < 9 || (hour == 13) || hour >= 18) {
                throw new DateTimeRangeException();
            }
        }catch (DateTimeParseException e){
            throw new InvalidDateTimeFormatException();
        }
    }

}

