package org.example.crudspringfjv.dao;

import org.example.crudspringfjv.domain.Cancion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CancionRepository extends JpaRepository<Cancion, Integer> {

    List<Cancion> findAll();

    List<Cancion> findByTitleContainingIgnoreCase(String title);

    Cancion save(Cancion song);
    void deleteById(int id);
}
