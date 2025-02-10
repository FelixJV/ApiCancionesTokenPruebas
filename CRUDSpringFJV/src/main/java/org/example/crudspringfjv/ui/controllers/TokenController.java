package org.example.crudspringfjv.ui.controllers;

import org.example.crudspringfjv.domain.User;
import org.example.crudspringfjv.service.UserService;
import org.example.crudspringfjv.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class TokenController {
    private final JwtUtils jwtUtils;
    private final UserService userService;

    public TokenController(JwtUtils jwtUtils, UserService userService) {
        this.jwtUtils = jwtUtils;
        this.userService = userService;
    }

    @PostMapping()
    public ResponseEntity<String> register(@RequestBody User user) {
        boolean isRegistered = userService.register(user.getNombre(), user.getPassword());

        if (isRegistered) {
            return ResponseEntity.ok("Usuario registrado con éxito");
        } else {
            return ResponseEntity.badRequest().body("El usuario ya existe");
        }
    }


    @GetMapping()
    public ResponseEntity<String> login(@RequestParam String nombre, @RequestParam String password) {
        boolean isAuthenticated = userService.login(nombre, password);

        if (isAuthenticated) {
            String token = jwtUtils.generateToken(nombre);
            return ResponseEntity.ok(token);
        } else {
            return ResponseEntity.status(401).body("Credenciales inválidas");
        }
    }
}
