package com.cibertec.projectefsrt.repositories;

import com.cibertec.projectefsrt.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Integer> {

    List<Cliente> findByEstadoCliAndNomClienteContaining(int estado, String nombre);

    Optional<Cliente> findTopByOrderByCodClienteDesc();

    List<Cliente> findByEstadoCli(int estado);
}
