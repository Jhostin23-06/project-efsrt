package com.cibertec.projectefsrt.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "empleado", schema = "bd_peliculas")
public class Empleado {
    @Id
    @Column(name = "id_empleado", nullable = false)
    private Integer id;

    @Column(name = "cod_empleado", nullable = false, length = 5)
    private String codEmpleado;

    @Column(name = "nom_empleado", nullable = false, length = 80)
    private String nomEmpleado;

    @Column(name = "dir_empleado", nullable = false, length = 80)
    private String dirEmpleado;

    @Column(name = "tel_empleado", nullable = false, length = 9)
    private String telEmpleado;

    @Column(name = "fechaing_emp", nullable = false)
    private LocalDate fechaingEmp;

    @ColumnDefault("1")
    @Column(name = "estado_emp", nullable = false)
    private Integer estadoEmp;

}