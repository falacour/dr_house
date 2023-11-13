
package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.servicios.ServicioUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping ("/")

public class PortalControlador {
    @Autowired
    private ServicioUsuario servicioUsuario;
 
    
    @GetMapping("/")
    public String index(){
        
        return "index.html";
    }
     @GetMapping("/registrar")
    public String registrar(){
        return "registrar.html";
    }
//    @PostMapping("/registro")
//    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password, @RequestParam String password2, ModelMap modelo){
//    
//        try {
//            servicioUsuario.registrar(nombre, email, password, password2);
//            modelo.put("exito", "Usuario registrado correctamente!");
//            
//            
//        } catch (MiException ex) {
//            modelo.put("error", ex.getMessage());
//            modelo.put("nombre",nombre);
//            modelo.put("email",email);
//            return "registrar.html";
//        }
//        return "index.html";
//     }
//   @ GetMapping("/login")
//    public String login(){
//        return "login.html";
//    }
    }
        
