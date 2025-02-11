package com.cibertec.projectefsrt.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Optional;

@Data
@Entity
@Table(name = "empleado", schema = "bd_peliculas")
public class Empleado {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    
    @Column(name = "estado_emp", nullable = false)
    private Integer estadoEmp;

    public Empleado() {
        this.estadoEmp = 1;
    }

}