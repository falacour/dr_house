package com.mrhouse.mrhouse.Entidades;

import com.mrhouse.mrhouse.enumeraciones.Rol;
import java.util.List;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Ente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String password;
    @OneToMany
    private List<Inmueble> inmueble;
    @OneToMany
    private List<Cliente> cliente;
    @OneToOne
    private Imagen imagen;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
