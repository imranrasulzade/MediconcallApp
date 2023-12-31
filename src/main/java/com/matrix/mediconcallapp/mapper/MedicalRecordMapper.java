package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.MedicalRecord;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.mapper.mappingUtil.MapPathUtility;
import com.matrix.mediconcallapp.model.dto.request.MedicalRecordReqDto;
import com.matrix.mediconcallapp.model.dto.response.MedicalRecordResp;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

@Mapper(componentModel = "spring")
public abstract class MedicalRecordMapper {

    @Value("${application.files.directory}")
    private String UPLOAD_DIR;


    @Mapping(source = "document", target = "documentPath", qualifiedByName = "mapToDocument")
    @Mapping(source = "doctorId", target = "doctor", qualifiedByName = "mapToDoctor")
    @Mapping(source = "patientId", target = "patient", qualifiedByName = "mapToPatient")
    public abstract MedicalRecord toMedicalRecord(MedicalRecordReqDto medicalRecordReqDto);


    @Mapping(source = "doctor.id", target = "doctorId")
    @Mapping(source = "patient.id", target = "patientId")
    @Mapping(source = "patient.user.name", target = "patientName")
    @Mapping(source = "doctor.user.name", target = "doctorName")
    public abstract MedicalRecordResp toMedicalRecordResp(MedicalRecord medicalRecord);


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


    @Named(value = "mapToDocument")
    public String mapToDocument(MultipartFile file){
           return MapPathUtility.mapPath(file, this.UPLOAD_DIR);
    }

}
