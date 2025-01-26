package com.cibertec.projectefsrt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Entity
@Table(name = "detalle_alquiler", schema = "bd_peliculas")
public class DetalleAlquiler {
    @EmbeddedId
    private DetalleAlquilerId id;

    @MapsId("idAlquiler")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_alquiler", nullable = false)
    private Alquiler idAlquiler;

    @MapsId("idPelicula")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula idPelicula;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    @Column(name = "precio", nullable = false, precision = 6, scale = 2)
    private BigDecimal precio;

}