package com.gabryel.api_funcionarios.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseJson {
    private HttpStatus status;
    private String message;
    private Object data;

    private int quantidade_itens;

    //Construtores


    public ResponseJson(HttpStatus status, String message, Object data, int quantidade_itens) {
        this.status = status;
        this.message = message;
        this.data = data;
        this.quantidade_itens = quantidade_itens;
    }

    public ResponseJson(HttpStatus status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public ResponseJson(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }


    public static ResponseEntity<?> build(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("message", message);
        response.put("status", status.value());
        return ResponseEntity.status(status).body(response);
    }

    //Getters and Setters
    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public int getQuantidade_itens() {
        return quantidade_itens;
    }

    public void setQuantidade_itens(int quantidade_itens) {
        this.quantidade_itens = quantidade_itens;
    }
}