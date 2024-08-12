package com.example.phonebook.handlers;

import com.example.phonebook.dtos.ErrorResponseDto;
import com.example.phonebook.exceptions.PhoneBookNotFoundException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Date;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PhoneBookNotFoundException.class)
    public ResponseEntity<ErrorResponseDto> handlePhoneBookNotFoundException(PhoneBookNotFoundException e) {
        ErrorResponseDto errorResponseDto = new ErrorResponseDto(
                HttpStatus.NOT_FOUND.value(),
                e.getMessage(),
                new Date()
        );
        return new ResponseEntity<ErrorResponseDto>(errorResponseDto, HttpStatus.NOT_FOUND);
    }
}
