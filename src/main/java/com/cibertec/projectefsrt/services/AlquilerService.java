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
        return alquilerRepository.findByEstadoAlq(1);
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

    public String generarSigCodAlquiler(){
        Optional<Alquiler> ultimoAlquiler = alquilerRepository.findTopByOrderByCodAlquilerDesc();
        if (ultimoAlquiler.isPresent()) {
            String ultimoCodigo = ultimoAlquiler.get().getCodAlquiler();
            int numero = Integer.parseInt(ultimoCodigo.substring(1)) + 1;
            return "R" + String.format("%06d", numero);
        } else {
            return "R000001";
        }
    }

}
