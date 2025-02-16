package com.cibertec.projectefsrt.entities;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "alquiler", schema = "bd_peliculas")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class Alquiler {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_alquiler", nullable = false)
    private Integer id;

    @Column(name = "cod_alquiler", nullable = false, length = 7)
    private String codAlquiler;

    @Column(name = "fecha_prest", nullable = false)
    private LocalDate fechaPrest;

    @Column(name = "fecha_dev", nullable = false)
    private LocalDate fechaDev;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_pelicula", nullable = false)
    private Pelicula idPelicula;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_empleado", nullable = false)
    private Empleado idEmpleado;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente idCliente;

    @ColumnDefault("1")
    @Column(name = "estado_alq", nullable = false)
    private Integer estadoAlq;

}