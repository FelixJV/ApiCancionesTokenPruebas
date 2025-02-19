package org.example.crudspringfjv.service;

import org.example.crudspringfjv.dao.CancionRepository;
import org.example.crudspringfjv.domain.Cancion;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CancionService {
    private final CancionRepository songDAO;
    private final UserService userService;

    public CancionService(CancionRepository songDAO, UserService userService) {
        this.songDAO = songDAO;
        this.userService = userService;
    }

    public List<Cancion> getAll() {
        return songDAO.findAll();
    }

    public Optional<Cancion> getById(int id) {
        return songDAO.findById(id);
    }

    public boolean addSong(Cancion song, String username) {
        if (!userService.isAdmin(username)) {
            throw new SecurityException("No tienes permiso para agregar canciones.");
        }
        songDAO.save(song);
        return true;
    }

    public void updateSong(Cancion song, String username) {
        if (!userService.isAdmin(username)) {
            throw new SecurityException("No tienes permiso para editar canciones.");
        }
        songDAO.save(song);
    }

    public void deleteSong(int id, String username) {
        if (!userService.isAdmin(username)) {
            throw new SecurityException("No tienes permiso para eliminar canciones.");
        }
        songDAO.deleteById(id);
    }
}
