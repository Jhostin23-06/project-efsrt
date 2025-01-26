package com.cibertec.projectefsrt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "alquiler", schema = "bd_peliculas")
public class Alquiler {
    @Id
    @Column(name = "id_alquiler", nullable = false)
    private Integer id;

    @Column(name = "cod_alquiler", nullable = false, length = 7)
    private String codAlquiler;

    @Column(name = "fecha_prest", nullable = false)
    private Instant fechaPrest;

    @Column(name = "fecha_dev", nullable = false)
    private Instant fechaDev;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado idEmpleado;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente idCliente;

    @ColumnDefault("1")
    @Column(name = "estado_alq", nullable = false)
    private Integer estadoAlq;

}