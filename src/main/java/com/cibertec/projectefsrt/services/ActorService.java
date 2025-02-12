package com.cibertec.projectefsrt.services;

import com.cibertec.projectefsrt.entities.Actor;
import com.cibertec.projectefsrt.repositories.ActorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ActorService {

    @Autowired
    private ActorRepository actorRepository;

    public List<Actor> findAll(){
        return actorRepository.findAll();
    }

    public Actor save(Actor actor){
        return actorRepository.save(actor);
    }

    public Actor findById(Integer id){
        return actorRepository.findById(id).orElse(null);
    }

    public void deleteById(Integer id){
        actorRepository.deleteById(id);
    }

    public List<Actor> buscarActoresActivos(String query){
        return actorRepository.findByEstadoActAndNomActorContaining(1, query);
    }

    public String generarSigCodActor(){
        Optional<Actor> ultimoActor = actorRepository.findTopByOrderByCodActorDesc();
        if (ultimoActor.isPresent()) {
            String ultimoCodigo = ultimoActor.get().getCodActor();
            int numero = Integer.parseInt(ultimoCodigo.substring(1)) + 1;
            return "A" + String.format("%04d", numero);
        } else {
            return "A0001";
        }
    }

    public List<Actor> findAllActive(){
        return actorRepository.findByEstadoAct(1);
    }

}
