package com.cibertec.projectefsrt.repositories;

import com.cibertec.projectefsrt.entities.Alquiler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlquilerRepository extends JpaRepository<Alquiler, Integer> {
    List<Alquiler> findByEstadoAlqAndCodAlquilerContaining(int estado, String codigo);
}
