package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Connection;
import com.matrix.mediconcallapp.model.dto.request.ConnectionRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;


@Mapper(componentModel = "spring")
public abstract class ConnectionMapper {

    @Mapping(source = "patientId", target = "patient", qualifiedByName = "mapToPatientId")
    @Mapping(source = "doctorId", target = "doctor", qualifiedByName = "mapToDoctorId")
    public abstract Connection toConnection(ConnectionRequestDto connectionRequestDto);

    @Named("mapToPatientId")
    public Patient mapToPatientId(Integer patientId){
        Patient patient = new Patient();
        patient.setId(patientId);
    return patient;
    }

    @Named("mapToDoctorId")
    public Doctor mapToDoctorId(Integer doctorId){
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        return doctor;
    }
}
