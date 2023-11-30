/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrhouse.mrhouse.servicios;

import com.mrhouse.Entidades.Cita;
import com.mrhouse.mrhouse.repositorios.RepositorioCita;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ServicioCita {

    @Autowired
    private RepositorioCita repositorioCita; // Cambiando el nombre del repositorio

    public List<Cita> obtenerTodasLasCitas() {
        return repositorioCita.findAll();
    }

    public Cita obtenerCitaPorId(String id) {
        return repositorioCita.findById(id).orElse(null);
    }

    public Cita crearCita(Cita nuevaCita) {
        // Puedes agregar lógica adicional aquí antes de guardar la cita, si es necesario
        return repositorioCita.save(nuevaCita);
    }

    public Cita actualizarCita(String id, Cita citaActualizada) {
        if (repositorioCita.existsById(id)) {
            // Puedes agregar lógica adicional aquí antes de actualizar la cita, si es necesario
            citaActualizada.setId(id); // Asegurarse de que el ID coincida
            return repositorioCita.save(citaActualizada);
        }
        return null; // La cita no existe, devolver null o lanzar una excepción según tus necesidades
    }

    public boolean eliminarCita(String id) {
        if (repositorioCita.existsById(id)) {
            // Puedes agregar lógica adicional aquí antes de eliminar la cita, si es necesario
            repositorioCita.deleteById(id);
            return true;
        }
        return false; // La cita no existe, no se eliminó nada
    }
}
