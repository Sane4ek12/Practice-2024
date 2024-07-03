package com.example.practice.exception;

public class NotEnoughQuantityException extends RuntimeException {

    public NotEnoughQuantityException(String message) {
        super(message);
    }
}
