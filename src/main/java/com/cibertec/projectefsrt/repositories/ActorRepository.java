package com.cibertec.projectefsrt.repositories;

import com.cibertec.projectefsrt.entities.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {
    List<Actor> findByEstadoAct(int estado);

    Optional<Actor> findTopByOrderByCodActorDesc();

    List<Actor> findByEstadoActAndNomActorContaining(int estado, String nombre);

}
