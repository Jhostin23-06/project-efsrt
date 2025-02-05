package com.cibertec.projectefsrt.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.cibertec.projectefsrt.entities.Empleado;

public interface EmpleadoRepository extends JpaRepository<Empleado, Integer> {}
