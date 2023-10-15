package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.exception.ReservationNotFoundException;
import com.matrix.mediconcallapp.mapper.ReservationMapper;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import com.matrix.mediconcallapp.repository.ReservationRepository;
import com.matrix.mediconcallapp.service.ReservationService;
import com.matrix.mediconcallapp.service.utility.TimeUtility;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationServiceImpl implements ReservationService {
    private final ReservationRepository reservationRepository;
    private final ReservationMapper reservationMapper;

    @Override
    public List<TimeDto> getAllTimes(Integer patientId, Integer doctorId){
        List<Reservation> reservations = reservationRepository.findByDoctorIdForIsAcceptedPatient(patientId, doctorId);
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
    }

    @Override
    public List<ReservationDto> getAllReservations() throws ReservationNotFoundException {
        return reservationRepository.findAll()
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Override
    public List<ReservationDto> getReservationsOfDoctor(Integer id) throws ReservationNotFoundException {
        return reservationRepository.findByDoctorId(id)
                .stream().map(reservationMapper::toReservationDto).toList();
    }

    @Override
    public List<ReservationDto> getReservationsOfPatient(Integer id) throws ReservationNotFoundException{
        return reservationRepository.findByPatientId(id)
                .stream().map(reservationMapper::toReservationDto).toList();
    }

}

