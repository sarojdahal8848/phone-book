package com.example.phonebook.handlers;

import com.example.phonebook.dtos.ErrorResponseDto;
import com.example.phonebook.exceptions.PhoneBookNotFoundException;
import jakarta.validation.ValidationException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PhoneBookNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePhoneBookNotFoundException(PhoneBookNotFoundException e) {
        List<Object> errors = new ArrayList<>();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        errors.add(errorMap);
        
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                errors,
                new Date()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> handleValidationException(MethodArgumentNotValidException e) {
        List<Object> errors = new ArrayList<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {

            Map<String, String> errorMap = new HashMap<>();
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put("title", fieldName);
            errorMap.put("message", errorMessage);
            errors.add(errorMap);
        });
        
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.UNPROCESSABLE_ENTITY.value(), 
                "Unprocessable Entity",
                errors,
                new Date()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ErrorResponseDto> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        List<Object> errors = new ArrayList<>();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        errors.add(errorMap);

        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.METHOD_NOT_ALLOWED.value(),
                "Method Not Allowed",
                errors,
                new Date()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.METHOD_NOT_ALLOWED);
    }
    
    @ExceptionHandler(InternalError.class)
    public ResponseEntity<ErrorResponseDto> handleInternalError(InternalError e) {
        List<Object> errors = new ArrayList<>();
        Map<String, String> errorMap = new HashMap<>();
        errorMap.put("message", e.getMessage());
        errors.add(errorMap);
        
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                errors,
                new Date()
        );
        return new ResponseEntity<>(errorResponseDto, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
