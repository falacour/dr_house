package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.*;
import com.mrhouse.mrhouse.enumeraciones.Rol;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import com.mrhouse.mrhouse.servicios.*;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/")

public class PortalControlador {

    @Autowired
    private ServicioUsuario servicioUsuario;
    @Autowired
    private ServicioInmueble servicioInmueble;
    @Autowired
    private ServicioEnte servicioEnte;
    @Autowired
    private ServicioCliente servicioCliente;
    @Autowired
    private RepositorioInmueble repositorioInmueble;

    @GetMapping("/")
    public String index(ModelMap modelo, HttpSession session) {
        List<Inmueble> inmuebles = servicioInmueble.listarInmuebles();
        modelo.addAttribute("inmuebles", inmuebles);
        if (session != null) {
            Cliente cliente = (Cliente) session.getAttribute("clientesession");
            modelo.addAttribute("cliente", cliente);
        } else {
            Cliente cliente = null;
            modelo.addAttribute("cliente", cliente);
        }
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
                servicioCliente.registrar(archivo, nombre, dni, email, password, password2);
            } else if (rol.equalsIgnoreCase("ente")) {
                servicioEnte.crearEnte(archivo, nombre, email, password, password2);
            } else {
                servicioUsuario.registrar(nombre, email, password, password2);
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
        Usuario logueado = (Usuario) session.getAttribute("usuariosession");

        return "index.html";
    }

    @GetMapping("/vistaInmueble")
    public String vistaInmueble() {

        return "vistaInmueble.html";
    }

    @GetMapping("/perfil")
    public String perfil(ModelMap modelo, HttpSession session) {

        Cliente cliente = (Cliente) session.getAttribute("clientesession");

        if (cliente != null) {
            List<Inmueble> inmuebles = repositorioInmueble.inmueblesPorCliente(cliente.getId());

            if (inmuebles != null) {
                modelo.put("cliente", cliente);
                modelo.put("inmuebles", inmuebles);
            } else {
                //caso en que la lista de inmuebles sea null
                modelo.put("cliente", cliente);
                ArrayList<Inmueble> vacio = new ArrayList<>();
                modelo.put("inmuebles", vacio);
            }
        }
        return "perfil.html";
    }
}
