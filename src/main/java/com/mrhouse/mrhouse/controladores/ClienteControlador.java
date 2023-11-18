/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.controladores;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 *
 * @author thell
 */
@Controller
@RequestMapping("/cliente")
public class ClienteControlador {

    @GetMapping("/registrar")
    public String registrar() {
        return "cliente_form.html";
    }

    @PostMapping("/registro")
    public String registro(@RequestParam String nombre, @RequestParam Integer dni, @RequestParam String email,
            @RequestParam String password, @RequestParam String password2) {
        System.out.println("nombre: " + nombre);
        return "cliente_registrar.html";
    }

}
