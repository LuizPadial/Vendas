package com.test.venda.domain.exceptions;


public class NegocioException extends RuntimeException{
    public NegocioException(String message) {
        super(message);
    }
}
