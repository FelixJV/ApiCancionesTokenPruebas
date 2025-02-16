package org.example.crudspringfjv.ui.controllers;

import org.example.crudspringfjv.domain.LoginResponse;
import org.example.crudspringfjv.domain.RefreshTokenRequest;
import org.example.crudspringfjv.domain.User;
import org.example.crudspringfjv.service.UserService;
import org.example.crudspringfjv.utils.Constantes;
import org.example.crudspringfjv.utils.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

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
       boolean flag = userService.register(user);
        return ResponseEntity.ok(flag);
    }


    @GetMapping()
    public ResponseEntity<LoginResponse> login(@RequestParam String nombre, @RequestParam String password) {
        boolean isAuthenticated = userService.login(nombre, password);
        LoginResponse loginResp = null;
        if (isAuthenticated) {
            String accessToken = jwtUtils.generateToken(nombre);
            String refreshToken = jwtUtils.generateRefreshToken(nombre);
            loginResp = new LoginResponse(accessToken, refreshToken);
        }
        return ResponseEntity.ok(loginResp);
    }
    @PostMapping("/refresh")
    public LoginResponse refresh(@RequestBody RefreshTokenRequest refreshRequest) {
        String refreshToken = refreshRequest.getRefreshToken();

        if (!jwtUtils.validateToken(refreshToken)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, Constantes.INVALID_TOKEN);
        }

        String username = jwtUtils.extractUsername(refreshToken);
        String newAccessToken = jwtUtils.generateToken(username);

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .build();
    }
    @GetMapping("/verificar/{codigo}")
    public void activateAccount(@PathVariable String codigo) {
        userService.verificar(codigo);
    }
}
