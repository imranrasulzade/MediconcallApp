package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;

import java.util.List;

public interface ReservationService {
    List<TimeDto> getAllTimes(Integer patientId, Integer doctorId);
    List<ReservationDto> getAllReservations();
    List<ReservationDto> getReservationsOfDoctor(Integer id);
    List<ReservationDto> getReservationsOfPatient(Integer id);
}
