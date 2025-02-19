package org.example.crudspringfjv.service;

import org.example.crudspringfjv.components.PasswordEncoder;
import org.example.crudspringfjv.components.excepciones.UserNoValidadoException;
import org.example.crudspringfjv.dao.RoleRepository;
import org.example.crudspringfjv.dao.UserRepository;
import org.example.crudspringfjv.domain.Role;
import org.example.crudspringfjv.domain.User;
import org.springframework.stereotype.Service;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserService {
    private final UserRepository userDAO;
    private final RoleRepository roleDAO;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userDAO, RoleRepository roleDAO, PasswordEncoder passwordEncoder) {
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public boolean login(String username, String password) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            try {
                return passwordEncoder.validatePassword(password, user.getPassword());
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                throw new UserNoValidadoException("Usuario no válido o error en la autenticación.");
            }
        }
        return false;
    }

    public boolean register(User user) {
        byte[] salt = new byte[16];
        SecureRandom sr = new SecureRandom();
        sr.nextBytes(salt);

        try {
            user.setPassword(passwordEncoder.createHash(user.getPassword()));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            throw new RuntimeException("Error en el hash de la contraseña", e);
        }

        Optional<Role> userRole = roleDAO.findByName("USER");
        Set<Role> roles = new HashSet<>();
        userRole.ifPresent(roles::add);
        user.setRoles(roles);

        userDAO.save(user);
        return true;
    }

    public boolean isAdmin(String username) {
        Optional<User> userOpt = userDAO.findByUsername(username);
        return userOpt.map(user -> user.getRoles().stream().anyMatch(role -> role.getName().equals("ADMIN"))).orElse(false);
    }
}
