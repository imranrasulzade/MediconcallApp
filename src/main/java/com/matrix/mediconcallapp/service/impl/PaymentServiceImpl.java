package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.exception.DoctorNotFoundException;
import com.matrix.mediconcallapp.exception.TransactionNotFoundException;
import com.matrix.mediconcallapp.mapper.PaymentMapper;
import com.matrix.mediconcallapp.model.dto.request.PaymentReqDto;
import com.matrix.mediconcallapp.model.dto.response.TransactionDto;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.repository.PaymentRepository;
import com.matrix.mediconcallapp.service.PaymentService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final PaymentMapper paymentMapper;
    private final JwtUtil jwtUtil;

    @Override
    public void add(HttpServletRequest request, PaymentReqDto reqDto) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        String patientBankAccount = patientRepository
                .findPatientByUserId(userId).getBankAccount();
        Doctor doctor = doctorRepository.findById(reqDto.getDoctorId())
                .orElseThrow(DoctorNotFoundException::new);
        reqDto.setPatientId(patientId);
        reqDto.setDoctorId(doctor.getId());
        reqDto.setSenderCard(patientBankAccount);
        reqDto.setReceiverCard(doctor.getBankAccount());
        reqDto.setTimestamp(LocalDateTime.now());
        paymentRepository.save(paymentMapper.toPayment(reqDto));
    }

    @Override
    public List<TransactionDto> getAllByReceiver(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return paymentRepository.findByDoctorId(doctorId)
                .orElseThrow(TransactionNotFoundException::new)
                .stream()
                .map(paymentMapper::toTransactionDto)
                .toList();
    }

    @Override
    public List<TransactionDto> getAllBySender(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        return paymentRepository.findByPatientId(patientId)
                .orElseThrow(TransactionNotFoundException::new)
                .stream()
                .map(paymentMapper::toTransactionDto)
                .toList();
    }

    @Override
    public TransactionDto getByReceiver(HttpServletRequest request, Integer id) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        return paymentRepository.findByIdAndDoctorId(id, doctorId)
                .map(paymentMapper::toTransactionDto)
                .orElseThrow(TransactionNotFoundException::new);
    }

    @Override
    public TransactionDto getBySender(HttpServletRequest request, Integer id) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        return paymentRepository.findByIdAndPatientId(id, patientId)
                .map(paymentMapper::toTransactionDto)
                .orElseThrow(TransactionNotFoundException::new);
    }
}
