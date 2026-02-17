package com.gabryel.api_funcionarios.exception.types;

public class NotFoundException extends RuntimeException {

    public NotFoundException() {
        super("Not Found");
    }
    public NotFoundException(String message) {
        super(message);
    }
}
