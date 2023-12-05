package com.mrhouse.mrhouse.Entidades;

import org.hibernate.annotations.GenericGenerator;
import javax.persistence.*;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
public class Imagen {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;
    private String mime;
    private String nombre;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    @Basic(fetch = FetchType.LAZY)
    private byte[] contenido;
}
