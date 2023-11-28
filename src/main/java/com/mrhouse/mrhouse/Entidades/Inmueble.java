package com.mrhouse.mrhouse.Entidades;

import javax.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Inmueble {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tipo;
    private Integer antiguedad;
    private Long mts2;
    private String direccion;
    private String provincia;
    private String departamento;
    private Double precio;
    @ManyToOne
    private Ente ente;
    @ManyToOne
    private Cliente cliente;
    @OneToOne
    private Imagen imagen;
    private Boolean alta;

}
