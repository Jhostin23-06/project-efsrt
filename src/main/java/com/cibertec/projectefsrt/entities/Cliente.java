package com.cibertec.projectefsrt.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "cliente", schema = "bd_peliculas")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_cliente", nullable = false)
    private Integer id;

    @NotBlank(message = "El código no puede estar vacío")
    @Column(name = "cod_cliente", nullable = false, length = 5)
    private String codCliente;

    @NotBlank(message = "El nombre no puede estar vacío")
    @Column(name = "nom_cliente", nullable = false, length = 80)
    private String nomCliente;

    @NotBlank(message = "La dirección no puede estar vacía")
    @Column(name = "dir_cliente", nullable = false, length = 80)
    private String dirCliente;

    @NotBlank(message = "El teléfono no puede estar vacío")
    @Column(name = "tel_cliente", nullable = false, length = 9)
    private String telCliente;

    @NotNull(message = "La fecha de ingreso no puede estar vacía")
    @Column(name = "fechaing_cli", nullable = false)
    private LocalDate fechaingCli;

    @ColumnDefault("1")
    @Column(name = "estado_cli", nullable = false)
    private Integer estadoCli;

    // Constructor sin argumentos
    public Cliente() {
        this.estadoCli = 1; // Asegurar valor predeterminado
    }

}