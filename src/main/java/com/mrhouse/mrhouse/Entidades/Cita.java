package com.mrhouse.mrhouse.Entidades;

import com.mrhouse.mrhouse.Entidades.Cliente;
import java.io.Serializable;
import javax.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Data
@NoArgsConstructor
public class Cita implements Serializable {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    @ManyToOne
    private Cliente ente;
    @ManyToOne
    private Cliente cliente;

    @ManyToOne
    private RangoHorario horario;

    private String Nota;

}
