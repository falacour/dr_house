package com.mrhouse.mrhouse.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
public class Publicacion {
    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String asunto;
    private String mensaje;
    @OneToOne
    private Cliente emisor;
    @OneToOne
    private Cliente receptor;  
    private boolean isLeido;
    private boolean estado;
}
