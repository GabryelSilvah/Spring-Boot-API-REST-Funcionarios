package com.gabryel.api_funcionarios.exception.types;

public class DuplicateNameException extends RuntimeException {

    public DuplicateNameException() {
        super("Informação inserida já existe.");
    }
    public DuplicateNameException(String message) {
        super(message);
    }
}
