package org.example.crudspringfjv.service;

import org.example.crudspringfjv.dao.Playlist;
import org.example.crudspringfjv.domain.Cancion;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PlaylistService {
    private final Playlist dataBase;

    public PlaylistService(Playlist dataBase) {
        this.dataBase = dataBase;
    }
    public List<Cancion> getAll(){ return dataBase.obtenerCanciones();}
    public void add(Cancion cancion){dataBase.agregarCancion(cancion);}
    public void delete(int id){dataBase.eliminarCancion(id);}
    public void update(Cancion cancion){dataBase.update(cancion);
    }
}
