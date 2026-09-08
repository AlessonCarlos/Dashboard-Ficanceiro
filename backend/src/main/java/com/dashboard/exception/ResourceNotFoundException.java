package com.dashboard.exception;

/**
 * Lancada quando uma entidade buscada por id nao existe no banco.
 * Tratada globalmente pelo GlobalExceptionHandler, que retorna 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

}
