package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Contact;
import com.matrix.mediconcallapp.model.dto.request.ContactDto;
import com.matrix.mediconcallapp.model.dto.response.ContactResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;



@Mapper(componentModel = "spring")
public abstract class ContactMapper {

    @Mapping(source = "patientId", target = "patient", qualifiedByName = "mapToPatientId")
    @Mapping(source = "doctorId", target = "doctor", qualifiedByName = "mapToDoctorId")
    public abstract Contact toContact(ContactDto contactDto);


    @Mapping(source = "patient", target = "patientId", qualifiedByName = "mapToPatientFromContact")
    @Mapping(source = "doctor", target = "doctorId", qualifiedByName = "mapToDoctorFromContact")
    public abstract ContactResponseDto toContactResponseDto(Contact contact);

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

    @Named("mapToPatientFromContact")
    public Integer mapToPatientFromContact(Patient patient){
        return patient.getId();
    }

    @Named("mapToDoctorFromContact")
    public Integer mapToDoctorFromContact(Doctor doctor){
        return doctor.getId();
}
}
