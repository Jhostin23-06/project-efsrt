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
public class DetalleAlquilerId implements java.io.Serializable {
    private static final long serialVersionUID = -6835015294517331688L;
    @Column(name = "id_alquiler", nullable = false)
    private Integer idAlquiler;

    @Column(name = "id_pelicula", nullable = false)
    private Integer idPelicula;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        DetalleAlquilerId entity = (DetalleAlquilerId) o;
        return Objects.equals(this.idAlquiler, entity.idAlquiler) &&
                Objects.equals(this.idPelicula, entity.idPelicula);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idAlquiler, idPelicula);
    }

}