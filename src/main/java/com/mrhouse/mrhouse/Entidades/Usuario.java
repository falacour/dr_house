package com.mrhouse.mrhouse.Entidades;

import com.mrhouse.mrhouse.enumeraciones.Rol;
import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String nombre;
    private String email;
    private String password;
    private String password2;
    @Enumerated(EnumType.STRING)
    private Rol rol;
}
