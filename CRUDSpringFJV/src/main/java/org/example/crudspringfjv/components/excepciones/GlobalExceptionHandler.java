package org.example.crudspringfjv.components.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, String>> handleGeneralException(Exception ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Ha ocurrido un error inesperado");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(SongNotFoundException.class)
    public ResponseEntity<Map<String, String>> handleSongNotFoundException(SongNotFoundException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Canción no encontrada");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(DuplicateUserException.class)
    public ResponseEntity<Map<String, String>> handleDuplicateUserException(DuplicateUserException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Usuario duplicado");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(TokenInvalidoException.class)
    public ResponseEntity<Map<String, String>> handleTokenInvalidException(TokenInvalidoException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Token inválido");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(TokenExpiradoException.class)
    public ResponseEntity<Map<String, String>> handleTokenExpiredException(TokenExpiradoException ex) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", "Token expirado");
        errorResponse.put("message", ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }

}
