package com.cibertec.projectefsrt.services;

import com.cibertec.projectefsrt.entities.Alquiler;
import com.cibertec.projectefsrt.repositories.AlquilerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;

    public List<Alquiler> findAll(){
        return alquilerRepository.findAll();
    }

    public Optional<Alquiler> findById(int id){
        return alquilerRepository.findById(id);
    }

    public Alquiler save(Alquiler alquiler){
        return alquilerRepository.save(alquiler);
    }

    public void deleteById(int id){
        alquilerRepository.deleteById(id);
    }

    public List<Alquiler> buscarAlquileresActivos(String query) {
        return alquilerRepository.findByEstadoAlqAndCodAlquilerContaining(1, query);
    }
}
