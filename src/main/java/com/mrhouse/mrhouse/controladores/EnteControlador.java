/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.Ente;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.servicios.ServicioEnte;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thell
 */
@Controller
@RequestMapping("/ente")
public class EnteControlador {
     @Autowired
    private ServicioEnte servicioEnte;
    
    @GetMapping("/registrar")
    public String registrar(){    
        return "ente_form.html";
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam String email, 
            @RequestParam String password,@RequestParam String password2, ModelMap modelo, MultipartFile archivo){
        try {
            servicioEnte.crearEnte(archivo, nombre, email, password, password2);
            
            modelo.put("exito", "Ente cargado con exito!");
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            
            return "ente_form.html";
        }        
        return "index.html";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo){
        List<Ente> entes = servicioEnte.listarEntes();
        
        modelo.addAttribute("entes", entes);
    
        return "ente_lista.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, ModelMap modelo){
        
        modelo.put("ente", servicioEnte.getOne(id));   
        
        return "ente_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id, String nombre, String email, 
            String password,String password2,MultipartFile archivo, ModelMap modelo){
        
        try {
            servicioEnte.modificarEnte(archivo, id, nombre, email, password, password2);
            
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            
            //Si hubiesen listas relacionadas se cargarian aca
            modelo.put("nombre", nombre);
            modelo.put("nombre", email);           
            
            return "ente_modificar.html";
        }
        return "redirect:../lista";
    }    
      
}
