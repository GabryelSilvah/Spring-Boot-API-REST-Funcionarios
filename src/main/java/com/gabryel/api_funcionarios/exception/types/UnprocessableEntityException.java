package com.gabryel.api_funcionarios.exception.types;

public class UnprocessableEntityException extends RuntimeException {

    public UnprocessableEntityException() {
        super("A requisição não pode ser atendida, pois não segue a regra de negócio da aplicação.");
    }
    public UnprocessableEntityException(String message) {
        super(message);
    }
}
