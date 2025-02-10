package org.example.crudspringfjv.dao;

import org.example.crudspringfjv.domain.Cancion;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class Playlist {

    private List<Cancion> playlist;
    private int contador;
    public Playlist() {
        playlist=new ArrayList<>();
        playlist.add(new Cancion(1, "Hola", "Quetal"));
        playlist.add(new Cancion(2, "Bohemian Rhapsody", "Queen"));
        playlist.add(new Cancion(3, "Stairway to Heaven", "Led Zeppelin"));
        playlist.add(new Cancion(4, "Imagine", "John Lennon"));
        playlist.add(new Cancion(5, "Smells Like Teen Spirit", "Nirvana"));
        playlist.add(new Cancion(6, "Hey Jude", "The Beatles"));
        playlist.add(new Cancion(7, "Sweet Child O' Mine", "Guns N' Roses"));
        playlist.add(new Cancion(8, "Billie Jean", "Michael Jackson"));
        playlist.add(new Cancion(9, "Wonderwall", "Oasis"));
        playlist.add(new Cancion(10, "Shape of You", "Ed Sheeran"));
        contador = playlist.size() + 1;
    }


    public List<Cancion> obtenerCanciones() {
        return playlist;
    }

    public void agregarCancion(Cancion nuevaCancion) {
        nuevaCancion.setId(contador);
        contador ++;
        playlist.add(nuevaCancion);
    }

    public void eliminarCancion(int id) {
        playlist.removeIf(cancion -> cancion.getId() == id);
    }
    public void update(Cancion cancionUpdated) {
        for (int i = 0; i < playlist.size(); i++) {
            if (playlist.get(i).getId() == cancionUpdated.getId()) {
                playlist.set(i, cancionUpdated);
            }
        }
    }
}

