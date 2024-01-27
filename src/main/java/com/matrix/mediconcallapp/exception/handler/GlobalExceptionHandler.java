package com.matrix.mediconcallapp.exception.handler;

import com.matrix.mediconcallapp.exception.BadRequestException;
import com.matrix.mediconcallapp.exception.ConflictException;
import com.matrix.mediconcallapp.exception.NotFoundException;
import com.matrix.mediconcallapp.model.dto.response.ErrorDetails;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.naming.AuthenticationException;
import java.util.Date;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex){
        log.error("NotFoundException ->  {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<String> handleConflictException(ConflictException ex){
        log.error("ConflictException ->  {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(ex.getMessage());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<String> handleBadRequestException(BadRequestException ex){
        log.error("BadRequestException ->  {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<String> handleAuthenticationException(AuthenticationException ex){
        log.error("AuthenticationException ->  {}", ex.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<String> handleMaxValueExceededException(ConstraintViolationException ex) {
        log.error("ConstraintViolationException ->  {}", ex.getMessage());
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        log.error("HttpMessageNotReadableException ->  {}", ex.getMessage());
        return new ResponseEntity<>("Invalid JSON data", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> userNull(MethodArgumentNotValidException ex, WebRequest webRequest) {
        log.error("RuntimeException -> MethodArgumentNotValidException {}", ex.getMessage());
        ErrorDetails errorDetails = ErrorDetails.builder()
                .timestamp(new Date())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .message(ex.getBindingResult().getFieldError().getField() + " is required ")
                .errorDetail(ex.getMessage())
                .path(webRequest.getDescription(false))
                .build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }


    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorDetails> userNull(RuntimeException ex, WebRequest webRequest) {
        log.error("RuntimeException -> RUNTIME {}", ex.getMessage());
        ErrorDetails errorDetails = ErrorDetails.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(ex.getMessage())
                .errorDetail(ex.getMessage()).path(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorDetails> userException(Exception ex, WebRequest webRequest) {
        log.error("Exception -> EXCEPTION {}", ex.getMessage());
        ErrorDetails errorDetails = ErrorDetails.builder().timestamp(new Date()).status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase()).message(ex.getMessage()).errorDetail(ex.getMessage())
                .path(webRequest.getDescription(false)).build();
        return new ResponseEntity<>(errorDetails, HttpStatus.BAD_REQUEST);
    }



}
