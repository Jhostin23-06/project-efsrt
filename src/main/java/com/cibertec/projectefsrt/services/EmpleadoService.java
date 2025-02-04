package com.cibertec.projectefsrt.services;

import java.util.List;

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
	
	public Empleado getById(Integer id) {
		
		return empleadoRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Empleado no encontrado"));
	}
}
