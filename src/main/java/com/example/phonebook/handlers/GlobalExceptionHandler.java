package com.example.phonebook.handlers;

import com.example.phonebook.dtos.ErrorResponseDto;
import com.example.phonebook.exceptions.PhoneBookNotFoundException;
import jakarta.validation.ValidationException;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.net.URI;
import java.util.*;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(PhoneBookNotFoundException.class)
    public ResponseEntity<ProblemDetail> handlePhoneBookNotFoundException(PhoneBookNotFoundException e) {
        List<String> errMessage = new ArrayList<>();
        Map<String, List<String>> fieldErrors = new HashMap<>();
        errMessage.add(e.getMessage());
        fieldErrors.put("error", errMessage);
        
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create("https://www.rfc-editor.org/rfc/rfc9110.html#name-404-not-found"));
        problemDetail.setTitle("Phone Book Not Found");
        problemDetail.setProperty("errors", fieldErrors);
        
        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ProblemDetail> handleValidationException(MethodArgumentNotValidException e) {
        Map<String, List<String>> errors = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach((error) -> {
            List<String> errMessage = new ArrayList<>();
            String fieldName = ((FieldError) error).getField();
            errMessage.add(error.getDefaultMessage());
            errors.put(fieldName, errMessage);
        });
        
        
        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        problemDetail.setType(URI.create("https://www.rfc-editor.org/rfc/rfc9110.html#name-400-bad-request"));
        problemDetail.setTitle("One or more validation errors occurred");
        problemDetail.setProperty("errors", errors);

        return new ResponseEntity<>(problemDetail, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ProblemDetail> handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        List<String> errMessage = new ArrayList<>();
        Map<String, List<String>> fieldErrors = new HashMap<>();
        errMessage.add(e.getMessage());
        fieldErrors.put("error", errMessage);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.METHOD_NOT_ALLOWED);
        problemDetail.setType(URI.create("https://www.rfc-editor.org/rfc/rfc9110.html#name-405-method-not-allowed"));
        problemDetail.setTitle("Method not allowed");
        problemDetail.setProperty("errors", fieldErrors);

        return new ResponseEntity<>(problemDetail, HttpStatus.METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<ProblemDetail> handleNoResourceFoundException(NoResourceFoundException e) {
        List<String> errMessage = new ArrayList<>();
        Map<String, List<String>> fieldErrors = new HashMap<>();
        errMessage.add(e.getMessage());
        fieldErrors.put("error", errMessage);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.NOT_FOUND);
        problemDetail.setType(URI.create("https://www.rfc-editor.org/rfc/rfc9110.html#name-404-not-found"));
        problemDetail.setTitle("Resource not found");
        problemDetail.setProperty("errors", fieldErrors);

        return new ResponseEntity<>(problemDetail, HttpStatus.NOT_FOUND);
    }
    
    @ExceptionHandler(InternalError.class)
    public ResponseEntity<ProblemDetail> handleInternalError(InternalError e) {
        List<String> errMessage = new ArrayList<>();
        Map<String, List<String>> fieldErrors = new HashMap<>();
        errMessage.add(e.getMessage());
        fieldErrors.put("error", errMessage);

        ProblemDetail problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setType(URI.create("https://www.rfc-editor.org/rfc/rfc9110.html#name-405-method-not-allowed"));
        problemDetail.setTitle("Internal Server Error");
        problemDetail.setProperty("errors", fieldErrors);

        return new ResponseEntity<>(problemDetail, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
}
