package com.matrix.mediconcallapp.controller;

import com.matrix.mediconcallapp.model.dto.request.PaymentReqDto;
import com.matrix.mediconcallapp.model.dto.response.TransactionDto;
import com.matrix.mediconcallapp.service.service_interfaces.PaymentService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;

    @PostMapping("/pay")
    @ResponseStatus(HttpStatus.CREATED)
    public void add(HttpServletRequest request,
                                    @RequestBody @Valid PaymentReqDto reqDto){
        paymentService.add(request, reqDto);
    }

    @GetMapping("/doctor/transactions")
    public List<TransactionDto> getAllByReceiver(HttpServletRequest request){
        return paymentService.getAllByReceiver(request);
    }

    @GetMapping("/patient/transactions")
    public List<TransactionDto> getAllBySender(HttpServletRequest request){
        return paymentService.getAllBySender(request);
    }


    @GetMapping("doctor/transaction/{id}")
    public TransactionDto getByReceiver(HttpServletRequest request,
                                                        @PathVariable @Pattern(regexp = "^[0-9]+$") Integer id){
        return paymentService.getByReceiver(request, id);
    }

    @GetMapping("patient/transaction/{id}")
    public TransactionDto getBySender(HttpServletRequest request,
                                                        @PathVariable @Pattern(regexp = "^[0-9]+$") Integer id){
        return paymentService.getBySender(request, id);
    }
}
