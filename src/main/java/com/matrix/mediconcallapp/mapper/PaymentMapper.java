package com.matrix.mediconcallapp.mapper;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.Patient;
import com.matrix.mediconcallapp.entity.Payment;
import com.matrix.mediconcallapp.model.dto.request.PaymentReqDto;
import com.matrix.mediconcallapp.model.dto.response.TransactionDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
public abstract class PaymentMapper {

    @Mapping(source = "patientId", target = "patient", qualifiedByName = "mapToPatient")
    @Mapping(source = "doctorId", target = "doctor", qualifiedByName = "mapToDoctor")
    public abstract Payment toPayment(PaymentReqDto reqDto);

    @Mapping(source = "patient.id", target = "senderId")
    @Mapping(source = "patient.user.name", target = "senderName")
    @Mapping(source = "patient.bankAccount", target = "senderCard", qualifiedByName = "mapToBankAccount")
    @Mapping(source = "doctor.id", target = "receiverId")
    @Mapping(source = "doctor.user.name", target = "receiverName")
    @Mapping(source = "doctor.bankAccount", target = "receiverCard", qualifiedByName = "mapToBankAccount")
    @Mapping(source = "timestamp", target = "timestamp", qualifiedByName = "mapToDate")
    public abstract TransactionDto toTransactionDto(Payment payment);


    @Named(value = "mapToDate")
    public String mapToDate(LocalDateTime timestamp){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return timestamp.format(formatter);
    }

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

    @Named(value = "mapToBankAccount")
    public String maskString(String s) {
        String visiblePart = s.substring(0, 4);
        String maskedPart = "********";
        return visiblePart + maskedPart + s.substring(12);
    }


}
