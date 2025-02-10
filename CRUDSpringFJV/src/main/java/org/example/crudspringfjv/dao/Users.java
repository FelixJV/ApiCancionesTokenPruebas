package org.example.crudspringfjv.dao;

import lombok.Data;
import org.example.crudspringfjv.domain.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Data
@Repository
public class Users {
    private final List<User> users = new ArrayList<>();


    public boolean add(User user) {
        if (users.stream().anyMatch(usr ->
                usr.getNombre().equals(user.getNombre()) || usr.getEmail().equals(user.getEmail())))
            return false;


        return users.add(user);
    }

    public void verificar(String codigo) {
        users.stream()
                .filter(usr -> usr.getCodigo().equals(codigo))
                .findFirst()
                .ifPresent(usr -> usr.setVerificado(true));
    }

    public List<User> getAll() {
        return new ArrayList<>(users);
    }
}
