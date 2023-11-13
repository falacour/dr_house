/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.Entidades;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author thell
 */
@Entity
public class Inmueble {

  @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String tipo;
    private Integer antiguedad;
    private Long mts2;
    private String direccion;
//    @ManyToOne
//    private Ente ente;
@OneToOne
private Imagen imagen; 
    public Inmueble() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Integer getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(Integer Antiguedad) {
        this.antiguedad = Antiguedad;
    }

    public Long getMts2() {
        return mts2;
    }

    public void setMts2(Long mts2) {
        this.mts2 = mts2;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }
//
//    public Ente getEnte() {
//        return ente;
//    }
//
//    public void setEnte(Ente ente) {
//        this.ente = ente;
//    }

    public Imagen getImagen() {
        return imagen;
    }

    public void setImagen(Imagen imagen) {
        this.imagen = imagen;
    }
    

}
