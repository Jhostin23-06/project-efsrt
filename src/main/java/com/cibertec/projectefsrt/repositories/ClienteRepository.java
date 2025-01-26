package com.cibertec.projectefsrt.repositories;

import com.cibertec.projectefsrt.entities.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository  extends JpaRepository<Cliente, Integer> {

    @Query("SELECT c FROM Cliente c WHERE c.nomCliente LIKE %:query% OR c.codCliente LIKE %:query%")
    List<Cliente> searchClientes(@Param("query") String query);

}
