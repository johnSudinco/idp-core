package com.idp_core.idp_core.domain.exception;

public class ClientAlreadyExistsException extends RuntimeException {

    public ClientAlreadyExistsException() {
        super("Client ID already exists");
    }

    public ClientAlreadyExistsException(String message) {
        super(message);
    }
}