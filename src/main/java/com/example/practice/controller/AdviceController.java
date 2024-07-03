package com.example.practice.controller;

import com.example.practice.exception.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class AdviceController {

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        final List<Violation> violations = e.getBindingResult().getFieldErrors().stream()
                .map(error -> new Violation(error.getField(), error.getDefaultMessage()))
                .collect(Collectors.toList());
        log.error("Ошибки при валидации полей: " + e.getMessage());
        return new ErrorResponse(400, "Ошибка при валидации данных!", violations);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onNotEnoughMoneyException(NotEnoughMoneyException e) {
        log.error(e.getMessage());
        return new ErrorResponse(400, e.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse onNotEnoughQuantityException(NotEnoughQuantityException e) {
        log.error(e.getMessage());
        return new ErrorResponse(400, e.getMessage(), null);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse onNoSuchElementException(NoSuchElementException e) {
        log.error(e.getMessage());
        return new ErrorResponse(404, e.getMessage(), null);
    }
}
