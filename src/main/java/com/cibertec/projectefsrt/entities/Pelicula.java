package com.cibertec.projectefsrt.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "pelicula", schema = "bd_peliculas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Pelicula {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pelicula", nullable = false)
    private Integer id;

    @Column(name = "cod_pelicula", nullable = false, length = 5)
    private String codPelicula;

    @Column(name = "nom_pelicula", nullable = false, length = 80)
    private String nomPelicula;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_genero", nullable = false)
    private Genero idGenero;

    @Column(name = "duracion", nullable = false)
    private Integer duracion;

    @Column(name = "anio", nullable = false)
    private Integer anio;

    @Column(name = "copias", nullable = false)
    private Integer copias;

    @ColumnDefault("1")
    @Column(name = "estado_pel", nullable = false)
    private Integer estadoPel = 1;

}