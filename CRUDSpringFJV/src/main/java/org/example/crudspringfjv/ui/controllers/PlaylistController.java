package org.example.crudspringfjv.ui.controllers;

import org.example.crudspringfjv.domain.Cancion;
import org.example.crudspringfjv.service.PlaylistService;
import org.example.crudspringfjv.utils.Constantes;
import org.example.crudspringfjv.utils.JwtUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("playlist")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final JwtUtils jwtUtils;

    public PlaylistController(PlaylistService playlistService, JwtUtils jwtUtils) {
        this.playlistService = playlistService;
        this.jwtUtils = jwtUtils;
    }

    @GetMapping()
    public ResponseEntity<?> getPlaylist(@RequestHeader("Authorization") String token) {
        if (!isTokenValido(token)) {
            return ResponseEntity.status(401).body(Constantes.INVALID_TOKEN);
        }

        List<Cancion> playlist = playlistService.getAll();
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getSong(@RequestHeader("Authorization") String token,
    @PathVariable Integer id) {
        if (!isTokenValido(token)) {
            return ResponseEntity.status(401).body(Constantes.INVALID_TOKEN);
        }

        List<Cancion> playlist = playlistService.getAll();
        Optional<Cancion> song = playlist.stream()
                .filter(cancion -> Objects.equals(cancion.getId(), id))
                .findFirst();

        if (song.isPresent()) {
            return ResponseEntity.ok(song.get());
        } else {
            return ResponseEntity.status(404).body(Constantes.NOT_FOUND_CANCION);
        }
    }

    @PostMapping()
    public ResponseEntity<?> add(
            @RequestHeader("Authorization") String token,
            @RequestBody Cancion cancion) {

        if (!isTokenValido(token)) {
            return ResponseEntity.status(401).body(Constantes.INVALID_TOKEN);
        }

        playlistService.add(new Cancion(playlistService.getAll().size() + 1, cancion.getNombre(), cancion.getArtista()));
        return ResponseEntity.ok("Canción agregada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id,
            @RequestBody Cancion cancion) {

        if (!isTokenValido(token)) {
            return ResponseEntity.status(401).body(Constantes.INVALID_TOKEN);
        }

        playlistService.update(new Cancion(id, cancion.getNombre(), cancion.getArtista()));
        return ResponseEntity.ok("Canción actualizada exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(
            @RequestHeader("Authorization") String token,
            @PathVariable Integer id) {

        if (!isTokenValido(token)) {
            return ResponseEntity.status(401).body(Constantes.INVALID_TOKEN);
        }

        playlistService.delete(id);
        return ResponseEntity.ok("Canción eliminada exitosamente");
    }

    private boolean isTokenValido(String token) {
        try {
            token = token.replace("Bearer ", "");
            String username = jwtUtils.extractUsername(token);
            return jwtUtils.validateToken(token, username);
        } catch (Exception e) {
            return false;
        }
    }
}
