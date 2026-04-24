package com.example.exception;

public class AccessDeniedException extends RuntimeException {
    public AccessDeniedException(String message) {
        super(message);
    }

    public AccessDeniedException(String resourceName, Long id, String role) {
        super(String.format("Access denied to %s with ID %d for role: %s", resourceName, id, role));
    }
}
