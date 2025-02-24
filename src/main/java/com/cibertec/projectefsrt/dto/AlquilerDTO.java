package com.cibertec.projectefsrt.dto;

import java.time.LocalDate;

import com.cibertec.projectefsrt.entities.Alquiler;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AlquilerDTO {
	
	private Integer id;
    private String codAlquiler;
    private LocalDate fechaPrest;
    private LocalDate fechaDev;
    private String nomPelicula;
    private String nomEmpleado;
    private String nomCliente;
    
    public AlquilerDTO(Alquiler alquiler) {
        this.id = alquiler.getId();
        this.codAlquiler = alquiler.getCodAlquiler();
        this.fechaPrest = alquiler.getFechaPrest();
        this.fechaDev = alquiler.getFechaDev();
        this.nomPelicula = alquiler.getIdPelicula().getNomPelicula();
        this.nomEmpleado = alquiler.getIdEmpleado().getNomEmpleado();
        this.nomCliente = alquiler.getIdCliente().getNomCliente();
    }

}
