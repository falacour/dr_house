/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

<<<<<<< Updated upstream
import com.mrhouse.mrhouse.Entidades.Imagen;
=======
>>>>>>> Stashed changes
import com.mrhouse.mrhouse.Entidades.Inmueble;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import com.mrhouse.mrhouse.servicios.ServicioImagen;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import java.util.List;
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
    
   @GetMapping("/registrar")
   public String registrar(ModelMap modelo){
       return "inmueble_form.html";
       
   }
   @PostMapping("/registro")
   public String registro(@RequestParam(required = false) Long id, @RequestParam String tipo, @RequestParam(required = false)Integer antiguedad,
           @RequestParam(required = false ) Long mts2, @RequestParam String direccion, ModelMap modelo, MultipartFile archivo ){
       try {
           servicioInmueble.crearInmueble(archivo, id, tipo, antiguedad, mts2, direccion);
           modelo.put("exelente", "se cargo tu inmueble");
       } catch (MiException e) {
           modelo.put("error", e.getMessage());
           return "index.html";
       }
       return "redirect:/";
       
   }
<<<<<<< Updated upstream
   
=======
>>>>>>> Stashed changes
   @GetMapping("/lista")
    public String listar(ModelMap modelo) {

        List<Inmueble> inmuebles = servicioInmueble.listarInmuebles();

        modelo.addAttribute("inmuebles", inmuebles);
        
        return "inmeueble_list.html";

    }
<<<<<<< Updated upstream
   
   @GetMapping("/modificar/{id}")
     public String modificar(@PathVariable Long id, ModelMap modelo){
         
         modelo.put("inmuebles", servicioInmueble.getOne(id));
         
         return "inmueble_modificar.html";
     }
     
     @PostMapping("/modificar/{id}")
     public String modificar(@PathVariable MultipartFile archivo, String id, ModelMap modelo){
        try {
            servicioImagen.actualizar(archivo, id);
        } catch (MiException ex) {
            modelo.put("error", ex);
            return "editorial_modificar.html";
        }
        
        return "redirect:../lista";
     }
=======
>>>>>>> Stashed changes
}
