package com.cibertec.projectefsrt.repositories;

import com.cibertec.projectefsrt.entities.Pelicula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PeliculaRepository extends JpaRepository<Pelicula, Integer> {
    List<Pelicula> findByEstadoPelAndNomPeliculaContaining(Integer estado, String nombre);

    Optional<Pelicula> findTopByOrderByCodPeliculaDesc();

    List<Pelicula> findByEstadoPel(Integer estado);

    List<Pelicula> findByIdGenero_Id(Integer idGenero);

    List<Pelicula> findByIdGenero_NomGenero(String nomGenero);
}
