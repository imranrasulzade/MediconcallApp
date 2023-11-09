package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.PaymentReqDto;
import com.matrix.mediconcallapp.model.dto.response.TransactionDto;
import com.matrix.mediconcallapp.service.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    public ResponseEntity<Void> add(HttpServletRequest request,
                                    @RequestBody @Valid PaymentReqDto reqDto){
        paymentService.add(request, reqDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/doctor/transactions")
    public ResponseEntity<List<TransactionDto>> getAllByReceiver(HttpServletRequest request){
        return ResponseEntity.ok(paymentService.getAllByReceiver(request));
    }

    @GetMapping("/patient/transactions")
    public ResponseEntity<List<TransactionDto>> getAllBySender(HttpServletRequest request){
        return ResponseEntity.ok(paymentService.getAllBySender(request));
    }


    @GetMapping("doctor/transaction/{id}")
    public ResponseEntity<TransactionDto> getByReceiver(HttpServletRequest request,
                                                        @PathVariable @Pattern(regexp = "^[0-9]+$") Integer id){
        return ResponseEntity.ok(paymentService.getByReceiver(request, id));
    }

    @GetMapping("patient/transaction/{id}")
    public ResponseEntity<TransactionDto> getBySender(HttpServletRequest request,
                                                        @PathVariable @Pattern(regexp = "^[0-9]+$") Integer id){
        return ResponseEntity.ok(paymentService.getBySender(request, id));
    }
}
