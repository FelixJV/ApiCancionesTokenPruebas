package org.example.crudspringfjv.ui.controllers;

import org.example.crudspringfjv.domain.Cancion;
import org.example.crudspringfjv.service.CancionService;
import org.example.crudspringfjv.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/songs")
public class CancionController {

    private final CancionService songService;
    private final UserService userService;

    public CancionController(CancionService songService, UserService userService) {
        this.songService = songService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<Cancion>> getAllSongs() {
        return ResponseEntity.ok(songService.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> getSongById(@PathVariable int id) {
        Optional<Cancion> cancion = songService.getById(id);
        return cancion.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping()
    public ResponseEntity<String> addSong(@RequestBody Cancion cancion, @RequestParam String username) {
        if (!userService.isAdmin(username)) {
            return ResponseEntity.status(403).body("No tienes permisos para agregar canciones.");
        }

        songService.addSong(cancion, username);
        return ResponseEntity.ok("Canción agregada exitosamente.");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateSong(
            @PathVariable int id,
            @RequestBody Cancion cancion,
            @RequestParam String username) {

        if (!userService.isAdmin(username)) {
            return ResponseEntity.status(403).body("No tienes permisos para editar canciones.");
        }

        cancion.setId(id);
        songService.updateSong(cancion, username);
        return ResponseEntity.ok("Canción actualizada exitosamente.");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteSong(
            @PathVariable int id,
            @RequestParam String username) {

        if (!userService.isAdmin(username)) {
            return ResponseEntity.status(403).body("No tienes permisos para eliminar canciones.");
        }

        songService.deleteSong(id, username);
        return ResponseEntity.ok("Canción eliminada exitosamente.");
    }
}

