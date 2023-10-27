package com.hunbk.springbootmail.global.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<ErrorResponse> handleException(CustomException e) {
        HttpStatus status = e.getErrorCode().getHttpStatus();
        ErrorResponse response = ErrorResponse.builder()
                .code(e.getErrorCode().name())
                .message(e.getErrorCode().getMessage())
                .build();

        return ResponseEntity.status(status)
                .body(response);
    }
}
