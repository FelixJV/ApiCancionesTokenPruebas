package org.example.crudspringfjv.components.excepciones;

public class UserNoValidadoException extends RuntimeException {
    public UserNoValidadoException(String message) {
        super(message);
    }
}

