package com.cibertec.projectefsrt.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import java.util.Objects;

@Getter
@Setter
@Embeddable
public class PeliculaActorId implements java.io.Serializable {
    private static final long serialVersionUID = -582798640324089251L;
    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Column(name = "id_actor", nullable = false)
    private Integer idActor;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        PeliculaActorId entity = (PeliculaActorId) o;
        return Objects.equals(this.idPelicula, entity.idPelicula) &&
                Objects.equals(this.idActor, entity.idActor);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idPelicula, idActor);
    }

}