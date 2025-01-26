package com.cibertec.projectefsrt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pelicula_actor", schema = "bd_peliculas")
public class PeliculaActor {
    @EmbeddedId
    private PeliculaActorId id;

    @MapsId("idPelicula")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula idPelicula;

    @MapsId("idActor")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_actor", nullable = false)
    private Actor idActor;

}