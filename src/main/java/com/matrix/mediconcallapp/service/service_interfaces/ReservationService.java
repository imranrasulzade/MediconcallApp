package com.matrix.mediconcallapp.service.service_interfaces;

import com.matrix.mediconcallapp.enums.ReservationStatus;
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

    List<ReservationDto> getByStatus(HttpServletRequest request, ReservationStatus status);

    void changeStatus(HttpServletRequest request, ReservationStatusDto reservationStatusDto);

    void cancel(HttpServletRequest request, Integer id);
}
