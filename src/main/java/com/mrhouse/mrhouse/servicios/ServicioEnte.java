/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.repositorios.RepositorioCliente;
import com.mrhouse.mrhouse.repositorios.RepositorioEnte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author thell
 */
@Service
public class ServicioEnte {

    @Autowired
    private RepositorioEnte repositorioEnte;
    @Autowired
    private ServicioImagen servicioImagen;
    @Autowired
    private RepositorioCliente repositorioCliente;

}
