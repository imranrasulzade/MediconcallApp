package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.exception.ReservationNotFoundException;
import com.matrix.mediconcallapp.mapper.ReservationMapper;
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

import java.util.ArrayList;
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

        boolean condition = contactRepository.findByDoctorAndPatient(doctorId, patientId).isPresent();
        if(condition){
            List<Reservation> reservations = reservationRepository.findByDoctorAndPatient(doctorId, patientId);
            List<TimeDto> checkedList = new ArrayList<>();
            if(!reservations.isEmpty()){
                List<TimeDto> timeDtoList = TimeUtility.getAllTimes();
                for(TimeDto t : timeDtoList){
                    for(Reservation reservation : reservations){
                        if(reservation.getDate().getDayOfMonth() == t.getDay() && reservation.getDate().getHour() == t.getHour()){
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
            throw new ReservationNotFoundException();
        }


    }

    @Override
    public List<ReservationDto> getAllReservations() throws ReservationNotFoundException {
        return reservationRepository.findAll()
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Override
    public List<ReservationDto> getReservationsOfDoctor(HttpServletRequest request) throws ReservationNotFoundException {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return reservationRepository.findByDoctorId(doctorId)
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Override
    public List<ReservationDto> getReservationsOfPatient(HttpServletRequest request) throws ReservationNotFoundException{
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        return reservationRepository.findByPatientId(userId)
                .stream().map(reservationMapper::toReservationDto).toList();
    }

}

