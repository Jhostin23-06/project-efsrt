package com.cibertec.projectefsrt.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "actor", schema = "bd_peliculas")
public class Actor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_actor", nullable = false)
    private Integer id;

    @Column(name = "cod_actor", nullable = false, length = 5)
    private String codActor;

    @Column(name = "nom_actor", nullable = false, length = 80)
    private String nomActor;

    @ColumnDefault("1")
    @Column(name = "estado_act", nullable = false)
    private Integer estadoAct;

    public Actor() {
        this.estadoAct = 1;
    }

}