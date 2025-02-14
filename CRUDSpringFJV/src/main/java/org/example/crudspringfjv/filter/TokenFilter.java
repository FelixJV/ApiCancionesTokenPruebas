package org.example.crudspringfjv.filter;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.example.crudspringfjv.components.excepciones.TokenExpiradoException;
import org.example.crudspringfjv.components.excepciones.TokenInvalidoException;
import org.example.crudspringfjv.utils.JwtUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class TokenFilter extends OncePerRequestFilter {
    private final JwtUtils jwtUtils;

    public TokenFilter(JwtUtils jwtUtils) {
        this.jwtUtils = jwtUtils;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        String requestURI = request.getRequestURI();

        if (requestURI.startsWith("/auth") || requestURI.startsWith("/auth/verificar/")) {
            filterChain.doFilter(request, response);
            return;
        }

        final String authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION);


        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Falta el token o el formato es incorrecto");
            return;
        }

        String token = authorizationHeader.substring(7);
        try {
            String username = jwtUtils.extractUsername(token);
            if (username != null && jwtUtils.validateToken(token, username)) {
                request.setAttribute("username", username);
                filterChain.doFilter(request, response);
            } else {
                throw new TokenInvalidoException("El token no es válido.");
            }
        } catch (ExpiredJwtException e) {
            throw new TokenExpiradoException("El token ha expirado.");
        }
    }

}

