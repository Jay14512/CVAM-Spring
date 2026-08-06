package com.cvam.cvam_v2_spring.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice //Tells Spring to intercept exceptions globally
public class GlobalExceptionHandler {

    //1. Handle Duplicate Emails
    @ExceptionHandler(EmailAlreadyRegisteredException.class)
    public ResponseEntity<ApiError> handleEmailConflict(EmailAlreadyRegisteredException ex) {
        HttpStatus status = HttpStatus.CONFLICT; //HTTP 409

        ApiError errorResponse = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage()//Automatically pulls "Email is already registered."
        );

        return new ResponseEntity<>(errorResponse, status);
    }

    //2. Handle Duplicate Fiscal Codes
    @ExceptionHandler(FiscalCodeAlreadyRegisteredException.class)
    public ResponseEntity<ApiError> handleFiscalCodeConflict(FiscalCodeAlreadyRegisteredException ex) {
        HttpStatus status = HttpStatus.CONFLICT; //HTTP 409

        ApiError errorResponse = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage() //Automatically pulls "Fiscal Code is already registered."
        );
        return new ResponseEntity<>(errorResponse, status);
    }

    //3. Fallback for unexpected system errors (like Eloquent/Database connection failures)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGeneralException(Exception ex) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR; //HTTP 500

        ApiError errorResponse = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                "An unexpected error occurred on the server."
        );

        return new ResponseEntity<>(errorResponse, status);
    }

}
