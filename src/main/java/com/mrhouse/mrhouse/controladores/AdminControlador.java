/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.servicios.ServicioCliente;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author thell
 */
@Controller
@RequestMapping("/admin")
public class AdminControlador {
    @Autowired
    private ServicioCliente servicioCliente;
    @Autowired
    private ServicioInmueble servicioInmueble;
    @GetMapping("/dashboard")
    public String panelAdministrativo(){
        return "panel.html";
    }
    @GetMapping("/usuarios")
    public String listar(ModelMap modelo){
        List<Cliente>clientes= servicioCliente.listarClientes();
        modelo.addAttribute("clientes",clientes);
        return "lista_cliente_admin.html";
    }
    @GetMapping("/cliente")
    public String crearCliente(){
     
        return "redirect:./registro";
    }
    
    @GetMapping("/modificarRol/{id}")
    public String cambiarRol(@PathVariable String id){
        servicioCliente.cambiarRol(id);
        
       return "redirect:/admin/usuarios";
    }
   
}
