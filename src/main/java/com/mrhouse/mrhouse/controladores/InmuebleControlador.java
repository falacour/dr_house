/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.*;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import com.mrhouse.mrhouse.servicios.ServicioCliente;
import com.mrhouse.mrhouse.servicios.ServicioImagen;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
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
@RequestMapping("/inmueble")
public class InmuebleControlador {

    @Autowired
    private ServicioInmueble servicioInmueble;
    @Autowired
    private RepositorioInmueble repositorioInmueble;
    @Autowired
    private ServicioImagen servicioImagen;
    @Autowired
    private ServicioCliente servicioCliente;
    
    //////////////////////////////////////REGISTRO///////////////////////////////////////
    @GetMapping("/registrar")
    public String registrar(ModelMap modelo) {
        return "inmueble_form.html";

    }

    @PostMapping("/registro")
    public String registro(@RequestParam(required = false) Long id, @RequestParam String tipo,
            @RequestParam(required = false) Integer antiguedad, @RequestParam(required = false) Long mts2,
            @RequestParam String direccion, ModelMap modelo, MultipartFile archivo, Double precio,
            String provincia, String departamento, String descripcion, HttpSession session,
            String transaccion, Integer hambientes ) {

        Cliente cliente = (Cliente) session.getAttribute("clientesession");

        try {
            servicioInmueble.crearInmueble(archivo, id, tipo, antiguedad, mts2, direccion,
                    precio, provincia, departamento, descripcion, cliente.getId(), transaccion,
                    hambientes);
            modelo.put("exelente", "se cargo tu inmueble");
        } catch (MiException e) {
            modelo.put("error", e.getMessage());
            System.out.println(e.getMessage());
            return "inmueble_form.html";
        }
        return "redirect:/";
    }
    
    //////////////////////////////////////LISTAS//////////////////////////////////////////
    @GetMapping("/listarTodosLosInmueblesAVender")
    public String listaTodosLosInmueblesAVender(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = repositorioInmueble.todosLosInmueblesAVender();
        modelo.addAttribute("inmuebles", inmuebles);
        modelo.addAttribute("cliente", cliente);
        return "inmueble_lista.html";
    }
    
    @GetMapping("/listarTodosLosInmueblesVendidos")
    public String listaTodosLosInmueblesVendidos(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = repositorioInmueble.todosLosInmueblesVendidos();
        modelo.addAttribute("inmuebles", inmuebles);
        modelo.addAttribute("cliente", cliente);
        return "inmueble_lista.html";
    }
    
    @GetMapping("/listaInmueblesNoComprados")
    public String listaEnteInmuebleNoComprados(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = repositorioInmueble.inmueblesNoComprados();
        modelo.addAttribute("inmuebles", inmuebles);
        modelo.addAttribute("cliente", cliente);
        return "inmueble_lista.html";
    }
    
    @GetMapping("/listaEnteInmuebleAVender")
    public String listaEnteInmuebleAVender(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = repositorioInmueble.inmueblesPorEnteAVender(cliente.getId());
        modelo.addAttribute("inmuebles", inmuebles);
        return "inmueble_lista.html";
    }

    @GetMapping("/listaVendidos")
    public String listarEnteInmueblesVendidos(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = repositorioInmueble.inmueblesPorEnteComprados(cliente.getId());
        modelo.addAttribute("inmuebles", inmuebles);
        return "inmueble_lista.html";
    }
    
    @GetMapping("/listaComprados")
    public String listarClienteInmueblesComprados(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = repositorioInmueble.inmueblesPorCliente(cliente.getId());
        modelo.addAttribute("inmuebles", inmuebles);
        return "inmueble_lista.html";
    }
    
    @GetMapping("/listaInmueblesPorEnteAVender")
    public String listarinmueblesPorEnteAVenders(ModelMap modelo, HttpSession session) {
        Cliente cliente = (Cliente) session.getAttribute("clientesession");
        List<Inmueble> inmuebles = repositorioInmueble.inmueblesPorCliente(cliente.getId());
        modelo.addAttribute("inmuebles", inmuebles);
        return "inmueble_lista.html";
    }
    
    /////////////////////////////////MODIFICAR/////////////////////////////////////
    @GetMapping("/modificar/{id}")
    public String modificar(@PathVariable Long id, ModelMap modelo) {

        modelo.put("inmueble", servicioInmueble.getOne(id));

        return "inmueble_modificar.html";
    }

    @PostMapping("/modificar/{id}")
    public String modificar(@PathVariable MultipartFile archivo, Long idInmueble, ModelMap modelo,
            String idImagen, String tipo, Integer antiguedad, Long mts2, String direccion,
            Double precio, String provincia, String departamento, String alta, String transaccion,
            Integer hambientes) {

        try {
            servicioImagen.actualizar(archivo, idImagen);
            Imagen imagen = servicioImagen.getOne(idImagen);
            servicioInmueble.modificar(archivo, idInmueble, tipo, antiguedad, mts2,
                    direccion, precio, provincia, departamento, alta, imagen, transaccion,
                    hambientes);

        } catch (MiException ex) {
            modelo.put("error", ex);
            return "inmueble_modificar.html";
        }

        return "redirect:/inmueble/listaEnteInmuebleAVender";
    }
    
    //////////////////////////////////////COMPRAR///////////////////////////////////////
    @GetMapping("/comprar/{id}")
    public String comprar(Long idInmueble, String idCliente) {

        servicioCliente.compra(idInmueble, idCliente);

        return "index.html";
    }
    
    //////////////////////////////////////Calendario///////////////////////////////////////
    @GetMapping("/calendario")
    public String definirReunion(ModelMap modelo) {

        return "calendario.html";
    }    
}
