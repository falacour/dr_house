package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.Entidades.Publicacion;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.servicios.ServicioCliente;
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
    @Autowired
    ServicioCliente servicioCliente;
    
    @PostMapping("/registrar")
    public String registrar(@RequestParam String id, ModelMap modelo){       
        modelo.put("idEnte", id);
        
        return "publicacion_form.html";   
    }
    
    @PostMapping("/registro")
    public String registro(@RequestParam String asunto,@RequestParam String mensaje,
            @RequestParam String idReceptor, ModelMap modelo, HttpSession session){
        try {
            
            Cliente emisor = (Cliente) session.getAttribute("clientesession");            
            
            servicioPublicacion.crearPublicacion(asunto, mensaje, emisor.getId(), idReceptor);
            
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
    @GetMapping("/recibidos")
    public String listarPublicacionesRecibidas(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        
        List <Publicacion> publicaciones = servicioPublicacion.listarPorIdReceptor(cliente.getId());
        modelo.addAttribute("publicaciones", publicaciones);
        return "publicaciones_lista_recibidos.html";
    }
    
    @GetMapping("/enviadas")
    public String listarPublicacionesEnviadas(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        
        List <Publicacion> publicaciones = servicioPublicacion.listarPorIdEmisor(cliente.getId());
        
        modelo.addAttribute("publicaciones", publicaciones);
        return "publicaciones_lista_recibidos.html";
    }
    
    @GetMapping("/visualizar/{id}")
    public String modificar(@PathVariable String id,ModelMap modelo, HttpSession session){
        
            Cliente cliente = (Cliente) session.getAttribute("clientesession");
            Publicacion publicacion = servicioPublicacion.getReferenceById(id);
            
            //la persona a la cual le tendria que responder el mail
            Cliente emisor = servicioCliente.getOne(publicacion.getEmisor().getId());
            
            //esto es para que si el que abre el mensaje es al que va dirigido, se marca como leido
            if(cliente.getId().equals(publicacion.getId())){
                servicioPublicacion.marcarComoLeido(publicacion.getId());
            } 
        
        
        modelo.addAttribute("publicacion", publicacion);
        modelo.put("emisor", emisor);
        
        return "publicacion_visualizar.html";
    }
    
    @PostMapping("/responder/{id}")
    public String modificar(@PathVariable String id,@RequestParam String asunto,
            @RequestParam String mensaje, @RequestParam String idEmisor,@RequestParam String idReceptor,ModelMap modelo){
        
        try {           
            System.out.println("idEmisor" + idEmisor);
            System.out.println("idReceptor" + idReceptor);
            
            //se invierten los id porque se tiene que responder
            servicioPublicacion.crearPublicacion(asunto, mensaje, idReceptor, idEmisor);
            modelo.put("exito", "Publicacion modificada con exito");
        
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("asunto", asunto);
            modelo.put("mensaje", mensaje);
        
            return "publicacion_visualizar.html";
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
