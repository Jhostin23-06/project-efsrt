package com.cibertec.projectefsrt.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "genero", schema = "bd_peliculas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Genero {
    @Id
    @Column(name = "id_genero", nullable = false)
    private Integer id;

    @Column(name = "cod_genero", nullable = false, length = 3)
    private String codGenero;

    @Column(name = "nom_genero", nullable = false, length = 60)
    private String nomGenero;

}