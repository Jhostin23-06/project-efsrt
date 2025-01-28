package com.cibertec.projectefsrt.repositories;

import com.cibertec.projectefsrt.entities.Genero;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GeneroRepository extends JpaRepository<Genero, Integer> {
    Genero findByNomGenero(String name);
}
