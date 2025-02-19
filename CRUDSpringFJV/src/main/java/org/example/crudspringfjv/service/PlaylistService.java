package org.example.crudspringfjv.service;

import org.example.crudspringfjv.dao.CancionRepository;
import org.example.crudspringfjv.dao.PlaylistRepository;
import org.example.crudspringfjv.domain.Cancion;
import org.example.crudspringfjv.domain.Playlist;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlaylistService {
    private final PlaylistRepository playlistDAO;
    private final CancionRepository songDAO;
    private final UserService userService;

    public PlaylistService(PlaylistRepository playlistDAO, CancionRepository songDAO, UserService userService) {
        this.playlistDAO = playlistDAO;
        this.songDAO = songDAO;
        this.userService = userService;
    }

    public List<Playlist> getAll() {
        return playlistDAO.findAll();
    }

    public boolean addSongToPlaylist(int playlistId, int songId) {
        Optional<Playlist> playlistOpt = playlistDAO.findById(playlistId);
        Optional<Cancion> songOpt = songDAO.findById(songId);

        if (playlistOpt.isPresent() && songOpt.isPresent()) {
            Playlist playlist = playlistOpt.get();
            playlist.getSongs().add(songOpt.get());
            playlistDAO.save(playlist);
            return true;
        }
        return false;
    }

    public void removeSongFromPlaylist(int playlistId, int songId, String username) {
        if (!userService.isAdmin(username)) {
            throw new SecurityException("No tienes permiso para eliminar canciones de una playlist.");
        }

        Optional<Playlist> playlistOpt = playlistDAO.findById(playlistId);
        if (playlistOpt.isPresent()) {
            Playlist playlist = playlistOpt.get();
            playlist.getSongs().removeIf(song -> song.getId()==songId);
            playlistDAO.save(playlist);
        }
    }
}

