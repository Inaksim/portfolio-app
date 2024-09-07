package com.portfolioapp.portfolio.app.controller;

import com.portfolioapp.portfolio.app.service.GlobalExceptionHandlerService;
import com.portfolioapp.portfolio.app.exception.ApplicationException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RestControllerAdvice
@AllArgsConstructor
public class GlobalExceptionHandlerController {

    private final GlobalExceptionHandlerService exceptionHandlerService;

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Map<String, Object>> handleExceptions(AuthenticationException e, WebRequest request) {
        return exceptionHandlerService.handleException(e, request);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleExceptions(HttpMessageNotReadableException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleExceptions(MethodArgumentNotValidException e) {
        return exceptionHandlerService.handleException(e);
    }

    @ExceptionHandler(ApplicationException.class)
    public ResponseEntity<Map<String, Object>> handleExceptions(ApplicationException e, WebRequest request) {
        return exceptionHandlerService.handleException(e, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleExceptions(Throwable e, WebRequest request) {
        return exceptionHandlerService.handleException(e, request);
    }
}
