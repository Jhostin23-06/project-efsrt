package com.cibertec.projectefsrt.services;

import com.cibertec.projectefsrt.dto.AlquilerDTO;
import com.cibertec.projectefsrt.entities.Alquiler;
import com.cibertec.projectefsrt.repositories.AlquilerRepository;
import com.cibertec.projectefsrt.utilidad.ReportGenerator;

import net.sf.jasperreports.engine.JRException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlquilerService {

    @Autowired
    private AlquilerRepository alquilerRepository;
    
    @Autowired
    private ReportGenerator reportGenerator;

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
    
    public byte[] exportPdf() throws JRException, FileNotFoundException {
    	List<AlquilerDTO> alquileresDTO = alquilerRepository.findAll()
    	        .stream()
    	        .map(AlquilerDTO::new) // Convertir Alquiler a AlquilerDTO
    	        .collect(Collectors.toList());

    	    return reportGenerator.exportToPdf(alquileresDTO); // Ahora pasamos la lista correcta
    }
    
    public byte[] exportXls() throws JRException, FileNotFoundException {
    	List<AlquilerDTO> alquileresDTO = alquilerRepository.findAll()
    	        .stream()
    	        .map(AlquilerDTO::new) // Convertir Alquiler a AlquilerDTO
    	        .collect(Collectors.toList());

    	    return reportGenerator.exportToXls(alquileresDTO); // Ahora pasamos la lista correcta
    }


}
