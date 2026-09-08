package com.dashboard.exception;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Formato padrao de resposta de erro devolvido pela API.
 */
public record ErrorResponse(
        LocalDateTime timestamp,
        int status,
        String error,
        String message,
        Map<String, String> validationErrors
) {
    public ErrorResponse(int status, String error, String message) {
        this(LocalDateTime.now(), status, error, message, null);
    }

    public ErrorResponse(int status, String error, String message, Map<String, String> validationErrors) {
        this(LocalDateTime.now(), status, error, message, validationErrors);
    }
}
