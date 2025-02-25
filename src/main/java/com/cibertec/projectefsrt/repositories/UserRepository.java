package com.cibertec.projectefsrt.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.cibertec.projectefsrt.entities.Usuario;

@Repository
public interface UserRepository extends JpaRepository<Usuario, Long> {
	
	Optional<Usuario> findByUsuario(String username);
	
}
