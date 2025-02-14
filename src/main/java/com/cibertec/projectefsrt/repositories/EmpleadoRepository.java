package com.cibertec.projectefsrt.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.projectefsrt.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {
	
	List<Empleado> findByEstadoEmpAndNomEmpleadoContaining(int estado, String nombre);
	
	Optional<Empleado> findTopByOrderByCodEmpleadoDesc();
}
