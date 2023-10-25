package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.ReservationRequestDto;
import com.matrix.mediconcallapp.model.dto.request.ReservationStatusDto;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import com.matrix.mediconcallapp.model.dto.response.TimeDto;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ReservationService {
    List<TimeDto> getAllTimes(HttpServletRequest request, Integer doctorId);
    List<ReservationDto> getAllReservations();
    List<ReservationDto> getReservationsOfDoctor(HttpServletRequest request);
    List<ReservationDto> getByPatient(HttpServletRequest request);

    void add(HttpServletRequest request, ReservationRequestDto requestDto);

    List<ReservationDto> getPendingStatus(HttpServletRequest request);

    void changeStatus(HttpServletRequest request, ReservationStatusDto reservationStatusDto);
}
