package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Rating;
import com.matrix.mediconcallapp.model.dto.request.RatingReqDto;
import com.matrix.mediconcallapp.model.dto.response.RatingRespDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public abstract class RatingMapper {

    @Mapping(source = "patientId", target = "raterPatient", qualifiedByName = "mapToPatient")
    @Mapping(source = "doctorId", target = "ratedDoctor", qualifiedByName = "mapToDoctor")
    public abstract Rating toRating(RatingReqDto dtoReq);



    @Mapping(source = "raterPatient", target = "raterPatientId", qualifiedByName = "mapToPatientId")
    @Mapping(source = "raterPatient", target = "patientFullName", qualifiedByName = "mapToPatientFullName")
    @Mapping(source = "ratedDoctor", target = "ratedDoctorId", qualifiedByName = "mapToDoctorId")
    @Mapping(source = "ratedDoctor", target = "doctorFullName", qualifiedByName = "mapToDoctorFullName")
    @Mapping(source = "timestamp", target = "timestamp", qualifiedByName = "mapToTimestamp")
    public abstract RatingRespDto toRatingRespDto(Rating rating);



    @Named(value = "mapToPatient")
    public Patient mapToPatient(Integer patientId){
        Patient patient = new Patient();
        patient.setId(patientId);
        return patient;
    }

    @Named(value = "mapToDoctor")
    public Doctor mapToDoctor(Integer doctorId){
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        return doctor;
    }

    @Named(value = "mapToPatientId")
    public Integer mapToPatientId(Patient patient){
        return patient.getId();
    }

    @Named(value = "mapToDoctorId")
    public Integer mapToDoctorId(Doctor doctor){
        return doctor.getId();
    }

    @Named(value = "mapToPatientFullName")
    public String mapToPatientFullName(Patient patient){
        return patient.getUser().getName() + " " + patient.getUser().getSurname();
    }

    @Named(value = "mapToDoctorFullName")
    public String mapToDoctorFullName(Doctor doctor){
        return doctor.getAcademicTitle() + " " + doctor.getUser().getName() + " " + doctor.getUser().getSurname();
    }

    @Named(value = "mapToTimestamp")
    public String mapToTimestamp(LocalDateTime dateTime){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return dateTime.format(formatter);
    }



}
