package com.gabryel.api_funcionarios.exception;

import com.gabryel.api_funcionarios.config.ResponseJson;
import com.gabryel.api_funcionarios.exception.types.BadRequestException;
import com.gabryel.api_funcionarios.exception.types.DuplicateNameException;
import com.gabryel.api_funcionarios.exception.types.NotFoundException;
import com.gabryel.api_funcionarios.exception.types.UnprocessableEntityException;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ControllerException extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<?> userNotFound(BadRequestException excetion) {
        return ResponseJson.build(HttpStatus.BAD_REQUEST, excetion.getMessage());
    }

    @ExceptionHandler(NullPointerException.class)
    public ResponseEntity<?> userNotFound(NullPointerException excetion) {
        return ResponseJson.build(HttpStatus.BAD_GATEWAY, "Erro temporário  no servidor - Status Code: 502.");
    }

    @ExceptionHandler(InvalidDataAccessApiUsageException.class)
    public ResponseEntity<?> userNotFound(InvalidDataAccessApiUsageException excetion) {
        return ResponseJson.build(HttpStatus.BAD_GATEWAY, "Erro temporário  no servidor - Status Code: 502.");
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<?> userNotFound(DataIntegrityViolationException excetion) {
        return ResponseJson.build(HttpStatus.BAD_GATEWAY, "Erro temporário  no servidor - Status Code: 502.");
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<?> userNotFound(ConstraintViolationException excetion) {
        return ResponseJson.build(HttpStatus.BAD_REQUEST, excetion.getMessage());
    }

    @ExceptionHandler(UnprocessableEntityException.class)
    public ResponseEntity<?> userNotFound(UnprocessableEntityException excetion) {
        return ResponseJson.build(HttpStatus.UNPROCESSABLE_ENTITY, excetion.getMessage());
    }

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<?> userNotFound(NotFoundException excetion) {
        return ResponseJson.build(HttpStatus.NOT_FOUND, excetion.getMessage());
    }

    @ExceptionHandler(DuplicateNameException.class)
    public ResponseEntity<?> userNotFound(DuplicateNameException excetion) {
        return ResponseJson.build(HttpStatus.NOT_FOUND, excetion.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> userNotFound(Exception excetion) {
        return ResponseJson.build(HttpStatus.BAD_GATEWAY, excetion.getMessage());
    }


}
