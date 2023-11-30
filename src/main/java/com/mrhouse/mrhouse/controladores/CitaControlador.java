/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrhouse.mrhouse.controladores;

import com.mrhouse.Entidades.Cita;
import com.mrhouse.mrhouse.servicios.ServicioCita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/citas")
public class CitaControlador {

    @Autowired
    private ServicioCita servicioCita; // Ahora se llama ServicioCita

    // Endpoint para obtener todas las citas
    @GetMapping
    public ResponseEntity<List<Cita>> obtenerTodasLasCitas() {
        List<Cita> citas = servicioCita.obtenerTodasLasCitas();
        return new ResponseEntity<>(citas, HttpStatus.OK);
    }

    // Endpoint para obtener una cita por su ID
    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable String id) {
        Cita cita = servicioCita.obtenerCitaPorId(id);
        if (cita != null) {
            return new ResponseEntity<>(cita, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para crear una nueva cita
    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody Cita nuevaCita) {
        Cita creada = servicioCita.crearCita(nuevaCita);
        return new ResponseEntity<>(creada, HttpStatus.CREATED);
    }

    // Endpoint para actualizar una cita existente
    @PutMapping("/{id}")
    public ResponseEntity<Cita> actualizarCita(@PathVariable String id, @RequestBody Cita citaActualizada) {
        Cita actualizada = servicioCita.actualizarCita(id, citaActualizada);
        if (actualizada != null) {
            return new ResponseEntity<>(actualizada, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    // Endpoint para eliminar una cita por su ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCita(@PathVariable String id) {
        if (servicioCita.eliminarCita(id)) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
