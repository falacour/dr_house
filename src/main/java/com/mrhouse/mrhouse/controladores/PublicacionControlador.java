package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.Entidades.Publicacion;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.servicios.ServicioPublicacion;
import java.util.List;
import javax.servlet.http.HttpSession;
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
             return "redirect:/";
        } catch (MiException ex) {
             modelo.put("error", ex.getMessage());
             modelo.put("asunto", asunto);
             modelo.put("mensaje", mensaje);              
        }
    return "registrar.html";
       
    }
    
    //Se listaran todas las publicaciones dirigidas al id del cliente registrado
    //no se necesitan parametros ya que se toman del session
    @GetMapping("/lista")
    public String listarPublicacionesMias(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        
        List <Publicacion> publicaciones = servicioPublicacion.listarPublicacionesPorId(cliente.getId());
        modelo.addAttribute("publicaciones", publicaciones);
        return "publicacion_lista.html";
    }
    
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo, HttpSession session){
        
            Cliente cliente = (Cliente) session.getAttribute("clientesession");
            Publicacion publicacion = servicioPublicacion.getReferenceById(id);
            
            //esto es para que si el que abre el mensaje es al que va dirigido, se marca como leido
            if(cliente.getId().equals(publicacion.getId())){
                servicioPublicacion.marcarComoLeido(publicacion.getId());
            } 
        
        
        modelo.addAttribute("publicacion", publicacion);
        
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
    
//    //cambia el estado a leido , y redirecciona a la vista donde se esta visualizando
//    @PostMapping("/marcarLeido/{id}")
//    public String marcarComoLeido(@PathVariable String id){
//        servicioPublicacion.marcarComoLeido(id);
//        
//        return "redirect:/modificar/"+id;
//    }
}
