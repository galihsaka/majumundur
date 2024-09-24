package com.enigmacamp.majumundur.controller;

import com.enigmacamp.majumundur.dto.response.CommonResponse;
import com.enigmacamp.majumundur.exception.ResourceNotFoundException;
import com.enigmacamp.majumundur.exception.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Optional;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler({ResourceNotFoundException.class})
    public ResponseEntity<CommonResponse<String>> handleResourceNotFoundException(ResourceNotFoundException ex) {
        CommonResponse<String> response = new CommonResponse<>();
        response.setStatusCode(HttpStatus.NOT_FOUND.value());
        response.setMessage(ex.getMessage());
        response.setData(Optional.empty());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    @ExceptionHandler({ValidationException.class})
    public ResponseEntity<CommonResponse<String>> handleValidationException(ValidationException ex) {
        CommonResponse<String> response=new CommonResponse<>();
        response.setStatusCode(HttpStatus.NOT_ACCEPTABLE.value());
        response.setMessage(ex.getMessage());
        response.setData(Optional.empty());
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(response);
    }

}
