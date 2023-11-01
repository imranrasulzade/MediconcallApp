package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Rating;
import com.matrix.mediconcallapp.model.dto.request.RatingReqDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public abstract class RatingMapper {

    @Mapping(source = "patientId", target = "raterPatient", qualifiedByName = "mapToPatient")
    @Mapping(source = "doctorId", target = "ratedDoctor", qualifiedByName = "mapToDoctor")
    public abstract Rating toRating(RatingReqDto dtoReq);


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



}
