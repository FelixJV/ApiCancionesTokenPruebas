package org.example.crudspringfjv.components.excepciones;

public class TokenExpiradoException extends RuntimeException {
    public TokenExpiradoException(String message) {
        super(message);
    }
}

