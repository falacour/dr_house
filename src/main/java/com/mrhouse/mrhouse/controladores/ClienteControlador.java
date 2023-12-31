/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

import java.util.List;
import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.enumeraciones.Rol;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioCliente;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import com.mrhouse.mrhouse.servicios.ServicioCliente;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @Autowired
    private ServicioCliente servicioCliente;
    
    @Autowired
    private RepositorioInmueble repositorioInmueble;
    @Autowired
    private RepositorioCliente repositorioCliente;

      @GetMapping("/registrar")
    public String registrar() {
        return "cliente_form.html";
    }

    @PostMapping("/registro")
    public String registro(MultipartFile archivo, @RequestParam String nombre, @RequestParam String dni, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2, ModelMap modelo) {
        try {
            servicioCliente.registrar(archivo, nombre, dni, email, password, password2, Rol.CLIENTE);
            modelo.put("exito", "Tu usuario de cliente fue guardado con exito");
        } catch (MiException ex) {
            modelo.put("error", ex.getMessage());
            modelo.put("nombre", nombre);
            modelo.put("email", email);
            return "cliente_form.html";
        }
        return "index.html";
    }


    @GetMapping("/modificar/{id}")
    public String modificar(ModelMap modelo, HttpSession session){
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        modelo.put("cliente",cliente);
        return "perfil_modificar.html";
    }
 
    
    @PostMapping("/modificar/{id}")
    public String modificado(MultipartFile archivo, String nombre, String dni, String email,
            String password, String password2, ModelMap modelo, String id, Rol rol){
        try {
            
            servicioCliente.actualizar(archivo, id, nombre, email, password, password2, dni, rol);
            
            return "index.html";
        } catch (MiException e) {
            return "perfil_modificar.html";
        }
    }
    
       @GetMapping("/lista")
    public String listar(ModelMap modelo,HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List <Cliente> clientes = repositorioInmueble.clientesDeEnte(cliente.getId());
        modelo.addAttribute("clientes", clientes);
        return "cliente_lista.html";
    }
    
    @GetMapping("/listaAllClient")
    public String listarTodosLosClientes(ModelMap modelo,HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List <Cliente> clientes = repositorioCliente.todosLosClientes();
        modelo.addAttribute("clientes", clientes);
        return "cliente_lista.html";
    }
    
    @GetMapping("/listaAllEnte")
    public String listarTodosLosEntes(ModelMap modelo,HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List <Cliente> clientes = repositorioCliente.todosLosEnte();
        modelo.addAttribute("clientes", clientes);
        return "cliente_lista.html";
    }
     @GetMapping("/baja/{id}")
public String baja(@PathVariable String id, @PathVariable Boolean baja ){
    servicioCliente.baja(id);

    return "cliente_lista.html";
}
}
