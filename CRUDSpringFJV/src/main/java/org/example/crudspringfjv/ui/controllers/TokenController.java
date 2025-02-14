package org.example.crudspringfjv.ui.controllers;

import org.example.crudspringfjv.domain.LoginResponse;
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
    public ResponseEntity<Boolean> register(@RequestBody User user) {
       boolean flag = userService.register(user.getNombre(), user.getPassword());
        return ResponseEntity.ok(flag);
    }


    @GetMapping()
    public ResponseEntity<LoginResponse> login(@RequestParam String nombre, @RequestParam String password) {
        boolean isAuthenticated = userService.login(nombre, password);

        if (isAuthenticated) {
            String accessToken = jwtUtils.generateToken(nombre);
            String refreshToken = jwtUtils.generateRefreshToken(nombre); 

            return ResponseEntity.ok(new LoginResponse(accessToken, refreshToken));
        }

        return ResponseEntity.status(401).build();
    }
}
