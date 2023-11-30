package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.Publicacion;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.servicios.ServicioPublicacion;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/publicacion")
public class PublicacionControlador {
    
    @Autowired
    ServicioPublicacion servicioPublicacion;
    
    @GetMapping("/registrar")
    public String registrar(){
        return "publicacion_form.html";   
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String asunto,@RequestParam String mensaje,
            @RequestParam String idEmisor,@RequestParam String idReceptor, ModelMap modelo){
        try {
            servicioPublicacion.crearPublicacion(asunto, mensaje, idEmisor, idReceptor);
            
            modelo.put("exito", "La publicacion se registro correctamente!!");
        } catch (MiException ex) {
             modelo.put("error", ex.getMessage());
             modelo.put("asunto", asunto);
             modelo.put("mensaje", mensaje);              
        }
    
        return "redirect:/";
    }
    
    @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List <Publicacion> publicaciones = servicioPublicacion.listarPublicaciones();
        modelo.addAttribute("publicaciones", publicaciones);
        return "publicacion_lista.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo){
        modelo.addAttribute("publicacion", servicioPublicacion.getReferenceById(id));
       
        
        return "publicacion_modificar.html";
    }
    
    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,@RequestParam String asunto,
            @RequestParam String mensaje, @RequestParam String idEmisor,@RequestParam String idReceptor,ModelMap modelo){
        
        try {
            servicioPublicacion.modificarPublicacion(id, asunto, mensaje, idEmisor, idReceptor);
            modelo.put("exito", "Publicacion modificada con exito");
        
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("asunto", asunto);
            modelo.put("mensaje", mensaje);
        
            return "publicacion_modificar.html";
        }       
        
        return "redirect:/lista";
    }
    
    //mostrar publicaciones para el administrador o ente
    
}
