package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.MedicalRecord;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import com.matrix.mediconcallapp.service.utility.MapPathUtility;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public abstract class MedicalRecordMapper {

    private static String UPLOAD_DIR = "C:\\Users\\imran\\Documents";


    @Mapping(source = "document", target = "documentPath", qualifiedByName = "mapToDocument")
    @Mapping(source = "doctorId", target = "doctor", qualifiedByName = "mapToDoctor")
    @Mapping(source = "patientId", target = "patient", qualifiedByName = "mapToPatient")
    public abstract MedicalRecord toMedicalRecord(MedicalRecordReqDto medicalRecordReqDto);


    @Mapping(source = "doctor", target = "doctorId", qualifiedByName = "mapToDoctorId")
    @Mapping(source = "patient", target = "patientId", qualifiedByName = "mapToPatientId")
    public abstract MedicalRecordResp toMedicalRecordResp(MedicalRecord medicalRecord);

    @Named(value = "mapToDoctorId")
    public Integer mapToDoctorId(Doctor doctor){
        return doctor.getId();
    }

    @Named(value = "mapToDoctor")
    public Doctor mapToDoctor(Integer doctorId){
        Doctor doctor = new Doctor();
        doctor.setId(doctorId);
        return doctor;
    }

    @Named(value = "mapToPatient")
    public Patient mapToPatient(Integer patientId){
        Patient patient = new Patient();
        patient.setId(patientId);
        return patient;
    }

    @Named(value = "mapToPatientId")
    public Integer mapToPatientId(Patient patient){
        return patient.getId();
    }

    @Named(value = "mapToDocument")
    public String mapToDocument(MultipartFile file){
           return MapPathUtility.mapPath(file, UPLOAD_DIR);
    }

}
