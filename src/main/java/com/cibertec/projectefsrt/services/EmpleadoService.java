package com.cibertec.projectefsrt.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cibertec.projectefsrt.entities.Empleado;
import com.cibertec.projectefsrt.repositories.EmpleadoRepository;

import jakarta.transaction.Transactional;

@Service
public class EmpleadoService {

	@Autowired
	EmpleadoRepository empleadoRepository;
	
	@Transactional
	public Empleado createEmpleado(Empleado empleado) {
		
		return empleadoRepository.save(empleado);
	}	
	
	public List<Empleado> readEmpleados() {
		
		return empleadoRepository.findAll();
	}
	
	@Transactional
	public Empleado updateEmpleado(Empleado empleado, Integer id) {
		
		Empleado empleadoExists = empleadoRepository.findById(id)
									.orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
		
		return empleadoRepository.save(empleadoExists);
	}
	
	public void deleteEmpleado(Integer id) {
		
		if(!empleadoRepository.existsById(id))
			
			throw new IllegalArgumentException("Empleado no existe");
			
		empleadoRepository.deleteById(id);
	}
	
	public Optional<Empleado> getById(Integer id) {
		
		return empleadoRepository.findById(id);
	}
	
	public List<Empleado> buscarEmpleadosActivos(String query){
		
        return empleadoRepository.findByEstadoEmpAndNomEmpleadoContaining(1,query);
    }
	
	public String generarSigCodEmpleado(){
		
        Optional<Empleado> ultimoEmpleado = empleadoRepository.findTopByOrderByCodEmpleadoDesc();
        
        if (ultimoEmpleado.isPresent()) {
        	
            String ultimoCodigo = ultimoEmpleado.get().getCodEmpleado();
            
            int numero = Integer.parseInt(ultimoCodigo.substring(1)) + 1;
            
            return "E" + String.format("%04d", numero);
        } else {
            return "E0001";
        }
    }
}
