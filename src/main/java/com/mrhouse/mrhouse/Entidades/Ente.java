package com.mrhouse.mrhouse.Entidades;

import com.mrhouse.mrhouse.enumeraciones.Rol;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import org.hibernate.annotations.GenericGenerator;

@Entity
public class Ente {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    private String nombre;
    private String email;
    private String password;
    @OneToOne
    private Imagen imagen;  
    //Esto no hace falta, ya que el Ente no deberia conocer sus inmuebles, basta con que el inmueble tenga el id del ente
    //asi que debe ir en Inmueble a que ente esta asociado(Hacer query personalizada para listar inmuebles por id del ente)
//    @OneToMany
//    private List<Inmueble> inmueble;

    @Enumerated(EnumType.STRING)
    private Rol rol;

    public Ente() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Rol getRol() {
        return rol;
    }

    public void setRol(Rol rol) {
        this.rol = rol;
    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }   
}
