package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Reservation;
import com.matrix.mediconcallapp.mapper.mappingUtil.MapDateUtility;
import com.matrix.mediconcallapp.model.dto.request.ReservationRequestDto;
import com.matrix.mediconcallapp.model.dto.response.ReservationDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public abstract class ReservationMapper {

    @Mapping(source = "doctor", target = "doctorFullName", qualifiedByName = "mapToDoctorFullName")
    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient", target = "patientFullName", qualifiedByName = "mapToPatientFullName")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "date", target = "date", qualifiedByName = "mapDateString")
    public abstract ReservationDto toReservationDto(Reservation reservation);

    @Mapping(source = "date", target = "date", qualifiedByName = "mapToLocalDateTime")
    public abstract Reservation toReservation(ReservationRequestDto requestDto);

    @Named(value = "mapToDoctorFullName")
    public String mapDoctorFullName(Doctor doctor){
        return doctor.getAcademicTitle() + ". " + doctor.getUser().getName() + " " + doctor.getUser().getSurname();
    }

    @Named(value = "mapToPatientFullName")
    public String mapPatientFullName(Patient patient){
        return patient.getUser().getName() + " " + patient.getUser().getSurname();
    }

    @Named(value = "mapDateString")
    public String mapDateString(LocalDateTime dateTime){
        return MapDateUtility.mapToDateString(dateTime);
    }

    @Named(value = "mapToLocalDateTime")
    public LocalDateTime mapToLocalDateTime(String date) {
        return MapDateUtility.mapToLocalDateTime(date);
    }



}
