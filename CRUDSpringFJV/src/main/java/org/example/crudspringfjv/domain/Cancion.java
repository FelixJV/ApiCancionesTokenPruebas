package org.example.crudspringfjv.domain;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Data
@NoArgsConstructor
@Component
public class Cancion {
    private  int id;
    private  String nombre;
    private  String artista;

    public Cancion(int id, String nombre, String artista) {
        this.id = id;
        this.nombre = nombre;
        this.artista = artista;
    }

}
