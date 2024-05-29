package com.nahorniak.inventorymanagementservice.controller;

import com.nahorniak.inventorymanagementservice.dto.response.ExceptionResponse;
import com.nahorniak.inventorymanagementservice.exception.ResourceNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashSet;
import java.util.Set;

import static org.springframework.http.HttpStatus.*;

@RestControllerAdvice
public class BaseController {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public ExceptionResponse handleValidationExceptions(MethodArgumentNotValidException exp, HttpServletRequest req) {
        Set<String> errors = new HashSet<>();
        exp.getBindingResult()
                .getAllErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .forEach(errors::add);

        return ExceptionResponse.builder()
                .reqId(req.getRequestId())
                .status(BAD_REQUEST.name())
                .code(BAD_REQUEST.value())
                .validationErrors(errors)
                .build();
    }

    @ExceptionHandler(BadCredentialsException.class)
    @ResponseStatus(FORBIDDEN)
    public ExceptionResponse handleException() {
        return ExceptionResponse.builder()
                .status(FORBIDDEN.name())
                .code(FORBIDDEN.value())
                .message("Login or Password is incorrect")
                .build();
    }


    @ExceptionHandler(ResourceNotFoundException.class)
    @ResponseStatus(NOT_FOUND)
    public ExceptionResponse handleResourceNotFound(ResourceNotFoundException ex, HttpServletRequest req) {
        return ExceptionResponse.builder()
                .reqId(req.getRequestId())
                .status(NOT_FOUND.name())
                .code(NOT_FOUND.value())
                .message(ex.getMessage())
                .build();
    }

//    @ExceptionHandler(Exception.class)
//    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
//    public ExceptionResponse handleException(Exception e, HttpServletRequest req){
//        return ExceptionResponse.builder()
//                .reqId(req.getRequestId())
//                .status(HttpStatus.INTERNAL_SERVER_ERROR.name())
//                .code(HttpStatus.INTERNAL_SERVER_ERROR.value())
//                .message("Internal error, please contact the admin")
//                .build();
//    }
}
