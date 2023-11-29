package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.*;
import com.mrhouse.mrhouse.enumeraciones.Rol;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.servicios.ServicioCliente;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")

public class PortalControlador {

  
    @Autowired
    private ServicioInmueble servicioInmueble;
 
    @Autowired
    private ServicioCliente servicioCliente;

    @GetMapping("/")
    public String index(ModelMap modelo, HttpSession session) {
        List<Inmueble> inmuebles = servicioInmueble.listarInmuebles();
        modelo.addAttribute("inmuebles", inmuebles);
        System.out.println("entro index");
        //Cliente cliente = (Cliente) session.getAttribute("clientesession");
        System.out.println("salio index");
        //modelo.addAttribute("cliente", cliente);
        System.out.println("re index");
        return "index.html";
    }

    @GetMapping("/registro")
    public String registrar() {
        return "registrar.html";
    }

    @PostMapping("/registrar")
    public String registro(@RequestParam String nombre, @RequestParam String email, @RequestParam String password,
            @RequestParam String password2, ModelMap modelo, String rol, String dni) {

        MultipartFile archivo = null;

        try {
            if (rol.equalsIgnoreCase("cliente")) {
                servicioCliente.registrar(archivo, nombre, dni, email, password, password2, Rol.CLIENTE);
            } else if (rol.equalsIgnoreCase("ente")) {
                servicioCliente.registrar(archivo, nombre, dni, email, password, password2, Rol.ENTE);
            }

            modelo.put("exito", "Usuario registrado correctamente!");
            return "redirect:/";

        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            modelo.put("password", password);
            modelo.put("password", password2);
            return "registrar.html";
        }

    }

    @GetMapping("/login")
    public String login(@RequestParam(required = false) String error, ModelMap modelo) {
        if (error != null) {
            modelo.put("error", "usuario o contraseña invalidos");
        }

        return "login.html";
    }

    // @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")
    @GetMapping("/inicio")
    public String inicio(HttpSession session, ModelMap modelo) {
        Cliente logueado = (Cliente) session.getAttribute("usuariosession");

        return "index.html";
    }

    @GetMapping("/vistaInmueble")
    public String vistaInmueble() {

        return "vistaInmueble.html";
    }

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {
        
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = cliente.getInmueble();
        modelo.addAttribute("cliente", cliente);
        modelo.put("inmuebles", inmuebles);
        
        return "perfil.html";
    }
}
