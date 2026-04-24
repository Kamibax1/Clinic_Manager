package com.example.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

public class ErrorResponse {
    @Getter @Setter
    private int status;

    @Getter @Setter
    private String message;

    @Getter @Setter
    private LocalDateTime timestamp;

    @Getter @Setter
    private List<String> errors;

    public ErrorResponse(int status, String message) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
    }

    public ErrorResponse(int status, String message, List<String> errors) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
}
