package com.example.libraryrestapi.exception;

import com.example.libraryrestapi.dto.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgumentException (IllegalArgumentException ex) {
        return new ResponseEntity<>(new ErrorResponse("400", ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<ErrorResponse> handleNullPointerException (NullPointerException ex) {
        return new ResponseEntity<>(new ErrorResponse("404", ex.getMessage()), HttpStatus.NOT_FOUND);
    }
}
