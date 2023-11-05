package com.matrix.mediconcallapp.service;

import com.matrix.mediconcallapp.model.dto.request.PaymentReqDto;
import com.matrix.mediconcallapp.model.dto.response.TransactionDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface PaymentService {

    void add(HttpServletRequest request, PaymentReqDto reqDto);

    List<TransactionDto> getAllByReceiver(HttpServletRequest request);
    List<TransactionDto> getAllBySender(HttpServletRequest request);

    TransactionDto getByReceiver(HttpServletRequest request, Integer id);
    TransactionDto getBySender(HttpServletRequest request, Integer id);
}
