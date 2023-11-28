package com.mrhouse.mrhouse.Entidades;

import com.mrhouse.mrhouse.enumeraciones.Rol;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Cliente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String dni;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private Rol rol;
    @OneToOne
    private Imagen imagen;
    //El mappedBy y el fetch es para que podamos acceder a la lista de inmuebles
    //sin problemas al cargarla desde cliente 
    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private List<Inmueble> inmueble;
    private Boolean permiso;
}
