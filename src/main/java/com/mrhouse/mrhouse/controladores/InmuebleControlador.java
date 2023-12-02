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
    
   @GetMapping("/registrar")
   public String registrar(ModelMap modelo){
       return "inmueble_form.html";
       
   }
   @PostMapping("/registro")
   public String registro(@RequestParam(required = false) Long id, @RequestParam String tipo,
           @RequestParam(required = false)Integer antiguedad, @RequestParam(required = false ) Long mts2,
           @RequestParam String direccion, ModelMap modelo, MultipartFile archivo, Double precio,
           String provincia, String departamento, String descripcion, HttpSession session){
       
       Cliente cliente = (Cliente) session.getAttribute("clientesession");
       
       try {
           servicioInmueble.crearInmueble(archivo, id, tipo, antiguedad, mts2, direccion,
                   precio, provincia, departamento, descripcion, cliente.getId());
           modelo.put("exelente", "se cargo tu inmueble");
       } catch (MiException e) {
           modelo.put("error", e.getMessage());
           return "inmueble_form.html";
       }
       return "redirect:/"; 
   }
   
   @GetMapping("/lista")
    public String listar(ModelMap modelo) {
        List <Inmueble> inmuebles = servicioInmueble.listarInmuebles();
        modelo.addAttribute("inmuebles", inmuebles);
        return "inmueble_lista.html";
    }
   
   @GetMapping("/modificar/{id}")
     public String modificar(@PathVariable Long id, ModelMap modelo){
         
         modelo.put("inmueble", servicioInmueble.getOne(id));
         
         return "inmueble_modificar.html";
     }
     
     @PostMapping("/modificar/{id}")
     public String modificar(@PathVariable MultipartFile archivo, Long idInmueble, ModelMap modelo,
             String idImagen,String tipo,Integer antiguedad, Long mts2, String direccion,
             Double precio, String provincia, String departamento, String alta){
 
        try {
            servicioImagen.actualizar(archivo, idImagen);
            Imagen imagen = servicioImagen.getOne(idImagen);
            servicioInmueble.modificar(archivo, idInmueble, tipo, antiguedad, mts2,
                    direccion, precio, provincia, departamento, alta, imagen);
            
        } catch (MiException ex) {
            modelo.put("error", ex);
            return "inmueble_modificar.html";
        }
        
        return "redirect:/inmueble/lista";
     }
     
     @GetMapping("/comprar/{id}")
     public String comprar(Long idInmueble, String id){
         
         servicioCliente.compra(idInmueble, id);
         
         return "redirect:/..";
     }
     
        @GetMapping("/calendario")
    public String definirReunion(ModelMap modelo) {
        
        return "calendario.html";
    }
}
