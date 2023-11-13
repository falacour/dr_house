/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.Entidades.Imagen;
import com.mrhouse.mrhouse.Entidades.Inmueble;
import com.mrhouse.mrhouse.excepciones.MiException;
import org.springframework.beans.factory.annotation.Autowired;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thell
 */
@Service
public class ServicioInmueble {
    @Autowired
    private RepositorioInmueble repositorioInmueble;
    @Autowired
   private ServicioImagen servicioImagen;
    @Transactional
    public void crearInmueble(MultipartFile archivo, Long id, String tipo, Integer antiguedad, Long mts2,
            String direccion) throws MiException{
        validar(mts2, tipo, antiguedad, mts2, direccion);
        Inmueble inmueble = new Inmueble();
        inmueble.setTipo(tipo);
        inmueble.setAntiguedad(antiguedad);
        inmueble.setMts2(mts2);
        inmueble.setDireccion(direccion);
        Imagen imagen= servicioImagen.guardar(archivo);
        inmueble.setImagen(imagen);
        repositorioInmueble.save(inmueble);
        
    }
     public List<Inmueble> listarInmuebles(){
        List<Inmueble>Inmuebles=new ArrayList<>();
        Inmuebles=repositorioInmueble.findAll();
        return Inmuebles;
        
    }
    public void modificar(MultipartFile archivo, Long id, String tipo, Integer antiguedad, Long mts2, String direccion) throws MiException {
        validar(id, tipo, antiguedad, mts2, direccion);
        Optional<Inmueble> respuesta = repositorioInmueble.findById(id);

        if (respuesta.isPresent()) {
            Inmueble inmueble = respuesta.get();
            inmueble.setTipo(tipo);
            inmueble.setAntiguedad(antiguedad);
	    inmueble.setMts2(mts2);
	    inmueble.setDireccion(direccion);
            String idImagen=null;
               if(inmueble.getImagen() != null){
              idImagen=inmueble.getImagen().getId();
          }
          Imagen imagen= servicioImagen.actualizar(archivo, idImagen);
          inmueble.setImagen(imagen);
            repositorioInmueble.save(inmueble);
        }
    }
   
    public Inmueble getOne(Long id){
        return repositorioInmueble.getOne(id);
        
    }
 public void validar(Long id, String tipo, Integer antiguedad, Long mts2, String direccion) throws MiException {
     if(id==null){
         throw new MiException("falta el id");
     }
        if (tipo == null ) {
            throw new MiException("el tipo no puede estar vacio o ser nulo");
        }
        if (antiguedad == null) {
            throw new MiException("ingrese la antiguedad");
        }
        if (mts2 == null) {
            throw new MiException("ingrese los metros cuadrados");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new MiException("el password no puede estar vacio o ser nulo");
        }
}
    }
