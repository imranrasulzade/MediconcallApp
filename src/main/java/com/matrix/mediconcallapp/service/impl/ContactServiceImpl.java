package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Contact;
import com.matrix.mediconcallapp.enums.ContactStatus;
import com.matrix.mediconcallapp.exception.child.ContactAlreadyExistsException;
import com.matrix.mediconcallapp.exception.child.ContactNotFoundException;
import com.matrix.mediconcallapp.exception.child.DoctorNotFoundException;
import com.matrix.mediconcallapp.mapper.ContactMapper;
import com.matrix.mediconcallapp.model.dto.request.ContactDto;
import com.matrix.mediconcallapp.model.dto.response.ContactResponseDto;
import com.matrix.mediconcallapp.repository.ContactRepository;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.service.service_interfaces.ContactService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {
    private final ContactRepository contactRepository;
    private final ContactMapper contactMapper;
    private final JwtUtil jwtUtil;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;


    public List<ContactResponseDto> getAllForPatient(HttpServletRequest request){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("contact getAllForPatient method started by userId:{} ", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        List<ContactResponseDto> contactResponseDtoList = contactRepository
                .findByPatientIdAndStatus(patientId, ContactStatus.ACCEPTED.getValue())
                .stream()
                .map(contactMapper::toContactResponseDto)
                .toList();
        log.info("contact getAllForPatient method done by userId: {}", userId);
        return contactResponseDtoList;

    }

    @Override
    public List<ContactResponseDto> getAllForDoctor(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("contact getAllForDoctor method started by userId:{} ", userId);
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        List<ContactResponseDto> contactResponseDtoList = contactRepository.findByDoctor(doctorId)
                .stream()
                .map(contactMapper::toContactResponseDto)
                .toList();
        log.info("contact getAllForDoctor method done by userId: {}", userId);
        return contactResponseDtoList;
    }

    @Override
    public Integer checkByPatient(HttpServletRequest request, Integer doctorId) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        doctorRepository.findById(doctorId).orElseThrow(DoctorNotFoundException::new);
        Optional<Contact> contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId);
        if(contact.isPresent()){
            return contact.get().getStatus();
        }
        return -1;
    }

    @Transactional
    @Override
    public void send(HttpServletRequest request, Integer doctorId) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("contact send method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        doctorRepository.findById(doctorId).orElseThrow(DoctorNotFoundException::new);
        boolean condition = contactRepository
                .findByDoctorIdAndPatientId(doctorId, patientId).isPresent();
        Contact contact;
        if(condition){
            contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId)
                    .orElseThrow(ContactNotFoundException::new);
            if(contact.getStatus() == ContactStatus.ACCEPTED.getValue()){
                throw new ContactAlreadyExistsException();
            }
            contact.setStatus(ContactStatus.PENDING.getValue());
            contact.setDeletedByUser(null);
        }else {
            ContactDto contactDto = new ContactDto();
            contactDto.setDoctorId(doctorId);
            contactDto.setPatientId(patientId);
            contactDto.setStatus(ContactStatus.PENDING.getValue());
            contact = contactMapper.toContact(contactDto);
        }
        contactRepository.save(contact);
        log.info("Contact record created by userId: {}", userId);
    }


    @Override
    public void accept(HttpServletRequest request, Integer patientId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("contact accept method started by userId: {}", userId);
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Contact contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId).
                orElseThrow(ContactNotFoundException::new);
        if(contact.getStatus().equals(ContactStatus.REMOVED.getValue())){
            throw new ContactNotFoundException();
        }else {
            contact.setStatus(ContactStatus.ACCEPTED.getValue());
            contactRepository.save(contact);
            log.info("Contact record update by userId: {}", userId);
        }
    }

    @Override
    public void deleteByDoctor(HttpServletRequest request, Integer patientId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("contact deleteByDoctor method started by userId: {}", userId);
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Contact contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId)
                        .orElseThrow(ContactNotFoundException::new);
        contact.setStatus(ContactStatus.REMOVED.getValue());
        contact.setDeletedByUser(userId);
        contactRepository.save(contact);
        log.info("Contact record deleted by userId: {}", userId);
    }


    @Override
    public void deleteByPatient(HttpServletRequest request, Integer doctorId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("contact deleteByPatient method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Contact contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId)
                .orElseThrow(ContactNotFoundException::new);
        contact.setStatus(ContactStatus.REMOVED.getValue());
        contact.setDeletedByUser(userId);
        contactRepository.save(contact);
        log.info("Contact record deleted by userId: {}", userId);
    }
}
