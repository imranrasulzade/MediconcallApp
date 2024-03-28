package com.matrix.mediconcallapp.service.impl;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.exception.child.DoctorNotFoundException;
import com.matrix.mediconcallapp.exception.child.TransactionNotFoundException;
import com.matrix.mediconcallapp.mapper.PaymentMapper;
import com.matrix.mediconcallapp.model.dto.request.PaymentReqDto;
import com.matrix.mediconcallapp.model.dto.response.TransactionDto;
import com.matrix.mediconcallapp.repository.DoctorRepository;
import com.matrix.mediconcallapp.repository.PatientRepository;
import com.matrix.mediconcallapp.repository.PaymentRepository;
import com.matrix.mediconcallapp.service.service_interfaces.PaymentService;
import com.matrix.mediconcallapp.service.utility.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

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
        log.info("payment add method started by userId: {}", userId);
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
        log.info("payment add by userId: {}", userId);
    }

    @Override
    public List<TransactionDto> getAllByReceiver(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("payment getAllByReceiver method started by userId: {}", userId);
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        List<TransactionDto> transactionDtoList = paymentRepository.findByDoctorId(doctorId)
                .orElseThrow(TransactionNotFoundException::new)
                .stream()
                .map(paymentMapper::toTransactionDto)
                .toList();
        log.info("payment getAllByReceiver method done by userId: {}", userId);
        return transactionDtoList;
    }

    @Override
    public List<TransactionDto> getAllBySender(HttpServletRequest request) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("payment getAllBySender method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        List<TransactionDto> transactionDtoList = paymentRepository.findByPatientId(patientId)
                .orElseThrow(TransactionNotFoundException::new)
                .stream()
                .map(paymentMapper::toTransactionDto)
                .toList();
        log.info("payment getAllBySender method done by userId: {}", userId);
        return transactionDtoList;
    }

    @Override
    public TransactionDto getByReceiver(HttpServletRequest request, Integer id) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("payment getByReceiver method started by userId: {}", userId);
        Integer doctorId = doctorRepository.findDoctorByUserId(userId).getId();
        TransactionDto transactionDto = paymentRepository.findByIdAndDoctorId(id, doctorId)
                .map(paymentMapper::toTransactionDto)
                .orElseThrow(TransactionNotFoundException::new);
        log.info("payment getByReceiver method done by userId: {}", userId);
        return transactionDto;
    }

    @Override
    public TransactionDto getBySender(HttpServletRequest request, Integer id) {
        Integer userId = jwtUtil.getUserId(jwtUtil.resolveClaims(request));
        log.info("payment getBySender method started by userId: {}", userId);
        Integer patientId = patientRepository.findPatientByUserId(userId).getId();
        TransactionDto transactionDto = paymentRepository.findByIdAndPatientId(id, patientId)
                .map(paymentMapper::toTransactionDto)
                .orElseThrow(TransactionNotFoundException::new);
        log.info("payment getBySender method done by userId: {}", userId);
        return transactionDto;
    }
}
