package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Contact;
import com.matrix.mediconcallapp.enums.ContactStatus;
import com.matrix.mediconcallapp.exception.child.ContactNotFoundException;
import com.matrix.mediconcallapp.mapper.ContactMapper;
import com.matrix.mediconcallapp.model.dto.request.ContactDto;
import com.matrix.mediconcallapp.model.dto.response.ContactResponseDto;
import com.matrix.mediconcallapp.repository.ContactRepository;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.service.ContactService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        return contactRepository.findByPatientIdAndStatus(patientId, ContactStatus.ACCEPTED.getValue())
                .stream()
                .map(contactMapper::toContactResponseDto)
                .toList();

    }

    @Override
    public List<ContactResponseDto> getAllForDoctor(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return contactRepository.findByDoctor(doctorId)
                .stream()
                .map(contactMapper::toContactResponseDto)
                .toList();
    }

    @Transactional
    @Override
    public void send(HttpServletRequest request, ContactDto contactDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        boolean condition = contactRepository
                .findByDoctorIdAndPatientId(contactDto.getDoctorId(), patientId).isPresent();
        Contact contact;
        if(condition){
            contact = contactRepository.findByDoctorIdAndPatientId(contactDto.getDoctorId(), patientId)
                    .orElseThrow(ContactNotFoundException::new);
            contact.setStatus(ContactStatus.PENDING.getValue());
            contact.setDeletedByUser(null);
        }else {
            contactDto.setPatientId(patientId);
            contactDto.setStatus(ContactStatus.PENDING.getValue());
            contact = contactMapper.toContact(contactDto);
        }
        contactRepository.save(contact);
    }


    @Override
    public void accept(HttpServletRequest request, Integer patientId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Contact contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId).
                orElseThrow(ContactNotFoundException::new);
        if(contact.getStatus().equals(ContactStatus.REMOVED.getValue())){
            throw new ContactNotFoundException();
        }else {
            contact.setStatus(ContactStatus.ACCEPTED.getValue());
            contactRepository.save(contact);
        }
    }

    @Override
    public void deleteByDoctor(HttpServletRequest request, Integer patientId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        Contact contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId)
                        .orElseThrow(ContactNotFoundException::new);
        contact.setStatus(ContactStatus.REMOVED.getValue());
        contact.setDeletedByUser(userId);
        contactRepository.save(contact);
    }


    @Override
    public void deleteByPatient(HttpServletRequest request, Integer doctorId){
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        Contact contact = contactRepository.findByDoctorIdAndPatientId(doctorId, patientId)
                .orElseThrow(ContactNotFoundException::new);
        contact.setStatus(ContactStatus.REMOVED.getValue());
        contact.setDeletedByUser(userId);
        contactRepository.save(contact);
    }
}
