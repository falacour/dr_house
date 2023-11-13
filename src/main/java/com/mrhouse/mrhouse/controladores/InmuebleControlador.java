/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/inmueble")
public class InmuebleControlador {
    
    @Autowired
    private ServicioInmueble servicioInmueble;
    @Autowired
    private RepositorioInmueble repositorioInmueble;
   @GetMapping("/registrar")
   public String registrar(ModelMap modelo){
       return "inmueble_form.html";
       
   }
   @PostMapping("/registro")
   public String registro(@RequestParam(required = false) Long id, @RequestParam String tipo, @RequestParam(required = false)Integer antiguedad,
           @RequestParam(required = false ) Long mts2, @RequestParam String direccion, ModelMap modelo, MultipartFile archivo ){
       try {
           servicioInmueble.crearInmueble(archivo, id, tipo, antiguedad, mts2, direccion);
           modelo.put("exelente", "se cargo tu inmueble");
       } catch (MiException e) {
           modelo.put("error", e.getMessage());
           return "index.html";
       }
       return "index.html";
       
   }
}
