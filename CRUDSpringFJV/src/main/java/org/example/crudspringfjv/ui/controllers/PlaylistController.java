package org.example.crudspringfjv.ui.controllers;

import org.example.crudspringfjv.domain.Playlist;
import org.example.crudspringfjv.service.PlaylistService;
import org.example.crudspringfjv.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/playlists")
public class PlaylistController {

    private final PlaylistService playlistService;
    private final UserService userService;

    public PlaylistController(PlaylistService playlistService, UserService userService) {
        this.playlistService = playlistService;
        this.userService = userService;
    }

    @GetMapping()
    public ResponseEntity<List<Playlist>> getAllPlaylists() {
        return ResponseEntity.ok(playlistService.getAll());
    }

    @PostMapping("/{playlistId}/addSong/{songId}")
    public ResponseEntity<String> addSongToPlaylist(
            @PathVariable int playlistId,
            @PathVariable int songId) {

        boolean added = playlistService.addSongToPlaylist(playlistId, songId);
        if (added) {
            return ResponseEntity.ok("Canción agregada a la playlist.");
        } else {
            return ResponseEntity.badRequest().body("No se pudo agregar la canción.");
        }
    }

    @DeleteMapping("/{playlistId}/removeSong/{songId}")
    public ResponseEntity<String> removeSongFromPlaylist(
            @PathVariable int playlistId,
            @PathVariable int songId,
            @RequestParam String username) {

        if (!userService.isAdmin(username)) {
            return ResponseEntity.status(403).body("No tienes permisos para eliminar canciones de la playlist.");
        }

        playlistService.removeSongFromPlaylist(playlistId, songId, username);
        return ResponseEntity.ok("Canción eliminada de la playlist.");
    }
}
