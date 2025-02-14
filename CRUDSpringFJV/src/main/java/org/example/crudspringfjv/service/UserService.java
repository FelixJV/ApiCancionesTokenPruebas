package org.example.crudspringfjv.service;

import org.example.crudspringfjv.dao.Users;
import org.example.crudspringfjv.domain.User;
import org.example.crudspringfjv.components.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final Users users;
    private final PasswordEncoder passwordEncoder;
    private final VerificarService verificarService;


    public UserService(Users users, PasswordEncoder passwordEncoder, VerificarService verificarService) {
        this.users = users;
        this.passwordEncoder = passwordEncoder;
        this.verificarService = verificarService;
    }

    public boolean login(String name, String pass) {
        List<User> regUsers = users.getUsers();

        Optional<User> userOpt = regUsers.stream()
                .filter(user -> user.getNombre().equals(name) && user.getPassword().equals(pass))
                .findFirst();

        return userOpt.isPresent();
    }

    public boolean register(User user) {
        byte[] salt = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);
        user.setCodigo(Base64.getUrlEncoder().encodeToString(salt));
        try {
            user.setPassword(passwordEncoder.createHash(user.getPassword()));
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException(e);
        }
        boolean flag = users.add(user);
        if (flag)
            verificarService.sendMail(user);
        return flag;
    }
    public void verificar(String codigo) {
        users.verificar(codigo);
    }

}
