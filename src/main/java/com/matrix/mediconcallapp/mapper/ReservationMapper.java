package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {

    @Mapping(source = "doctor", target = "doctorFullName", qualifiedByName = "mapToDoctorFullName")
    @Mapping(source = "patient", target = "patientFullName", qualifiedByName = "mapToPatientFullName")
    @Mapping(source = "date", target = "day", qualifiedByName = "mapToDay")
    @Mapping(source = "date", target = "hour", qualifiedByName = "mapToHour")
    @Mapping(source = "date", target = "month", qualifiedByName = "mapToMonth")
    public abstract ReservationDto toReservationDto(Reservation reservation);

    public abstract Reservation toReservation(ReservationDto reservationDto);

    @Named(value = "mapToDoctorFullName")
    public String mapDoctorFullName(Doctor doctor){
        return doctor.getAcademicTitle() + ". " + doctor.getUser().getName() + " " + doctor.getUser().getSurname();
    }

    @Named(value = "mapToPatientFullName")
    public String mapPatientFullName(Patient patient){
        return patient.getUser().getName() + " " + patient.getUser().getSurname();
    }

    @Named(value = "mapToDay")
    public Integer mapToDay(LocalDateTime dateTime){
        return dateTime.getDayOfMonth();
    }

    @Named(value = "mapToHour")
    public Integer mapToHour(LocalDateTime dateTime){
        return dateTime.getHour();
    }

    @Named(value = "mapToMonth")
    public Integer mapToMonth(LocalDateTime dateTime){
        return dateTime.getMonthValue();
    }
}
