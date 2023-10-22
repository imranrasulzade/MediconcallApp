package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReservationService {
    List<TimeDto> getAllTimes(HttpServletRequest request, Integer doctorId);
    List<ReservationDto> getAllReservations();
    List<ReservationDto> getReservationsOfDoctor(HttpServletRequest request);
    List<ReservationDto> getReservationsOfPatient(HttpServletRequest request);
}
