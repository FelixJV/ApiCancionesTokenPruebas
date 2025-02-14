package org.example.crudspringfjv.ui.controllers;

import org.example.crudspringfjv.domain.Cancion;
import org.example.crudspringfjv.service.PlaylistService;
import org.example.crudspringfjv.utils.Constantes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping(Constantes.PLAYLIST_VIEW)
public class PlaylistController {

    private final PlaylistService playlistService;

    public PlaylistController(PlaylistService playlistService) {
        this.playlistService = playlistService;
    }

    @GetMapping()
    public ResponseEntity<List<Cancion>> getPlaylist() {
        List<Cancion> playlist = playlistService.getAll();
        return ResponseEntity.ok(playlist);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cancion> getSong(@PathVariable Integer id) {

        List<Cancion> playlist = playlistService.getAll();
        Optional<Cancion> song = playlist.stream()
                .filter(cancion -> Objects.equals(cancion.getId(), id))
                .findFirst();
        Cancion filteredCancion = new Cancion();
        if(song.isPresent()){
            filteredCancion = song.get();
        }
            return ResponseEntity.ok(filteredCancion);
    }

    @PostMapping()
    public ResponseEntity<String> add(@RequestBody Cancion cancion) {
        playlistService.add(new Cancion(playlistService.getAll().size() + 1, cancion.getNombre(), cancion.getArtista()));
        return ResponseEntity.ok("Canción agregada exitosamente");
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> update(
            @PathVariable Integer id,
            @RequestBody Cancion cancion) {

        playlistService.update(new Cancion(id, cancion.getNombre(), cancion.getArtista()));
        return ResponseEntity.ok("Canción actualizada exitosamente");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Integer id) {

        playlistService.delete(id);
        return ResponseEntity.ok("Canción eliminada exitosamente");
    }
}
