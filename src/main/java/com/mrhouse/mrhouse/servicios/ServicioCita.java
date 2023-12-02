package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.Entidades.Cita;
import com.mrhouse.mrhouse.Entidades.FechaHoraContainer;
import com.mrhouse.mrhouse.Entidades.RangoHorario;
import com.mrhouse.mrhouse.Entidades.Cliente;

import com.mrhouse.repositorios.RepositorioCita;
import com.mrhouse.mrhouse.repositorios.RepositorioCliente;
import com.mrhouse.mrhouse.repositorios.RepositorioRangoHorario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioCita {

    @Autowired
    RepositorioCita repositorioCita;
    @Autowired
    RepositorioCliente repositorioCliente;

    @Autowired
    RepositorioRangoHorario repositorioRangoHorario;

    @Transactional
    public void crearCita(String idEnte, String idCliente, Long idHorario, String nota) {
        try {
            Optional<Cliente> enteOptional = repositorioCliente.findById(idEnte);
            Optional<Cliente> clienteOptional = repositorioCliente.findById(idCliente);
            Optional<RangoHorario> rangoHorarioOptional = repositorioRangoHorario.findById(idHorario);

            if (rangoHorarioOptional.isPresent() && enteOptional.isPresent() && clienteOptional.isPresent()) {
                RangoHorario rangoHorario = rangoHorarioOptional.get();
                Cliente ente = enteOptional.get();
                Cliente cliente = clienteOptional.get();

                Cita cita = new Cita();
                cita.setCliente(cliente);
                cita.setEnte(ente);
                cita.setHorario(rangoHorario);  // Aquí deberías pasar el objeto RangoHorario, no una lista
                cita.setNota(nota);

                repositorioCita.save(cita);
            } else {
                throw new IllegalArgumentException("El RangoHorario con ID " + idHorario + " no existe.");
            }
        } catch (Exception e) {
            // Maneja la excepción apropiadamente, loguea o lanza una excepción específica si es necesario
            e.printStackTrace();
            throw new RuntimeException("Error al crear la cita.", e);
        }
    }

    @Transactional
    public void modificarCita(String id, String idEnte, String idCliente, String idHorario, String nota) {
        ///  Validar(Usuario ente,Usuario cliente, RangoHorario horario);

        Optional<Cita> respuesta = repositorioCita.findById(id);
        Optional<Cliente> respuestaEnte = repositorioCliente.findById(idEnte);
        Optional<Cliente> respuestaCliente = repositorioCliente.findById(idCliente);
        Optional<RangoHorario> respuestaHorario = Optional.ofNullable((RangoHorario) repositorioRangoHorario.findById(idHorario));

        Cliente ente = new Cliente();
        Cliente cliente = new Cliente();
        RangoHorario horario = new RangoHorario();

        if (respuestaEnte.isPresent()) {
            ente = respuestaEnte.get();
        }
        if (respuestaCliente.isPresent()) {
            cliente = respuestaCliente.get();
        }
        if (respuestaHorario.isPresent()) {
            horario = respuestaHorario.get();
        }
        if (respuesta.isPresent()) {

            Cita cita = respuesta.get();

            cita.setCliente(cliente);
            cita.setEnte(ente);
            cita.setHorario(horario);
            cita.setNota(nota);

            repositorioCita.save(cita);
        }
    }

    public List<LocalTime> obtenerHorariosDisponibles(LocalTime inicio, LocalTime fin) {
        List<LocalTime> horarios = new ArrayList<>();
        while (inicio.isBefore(fin) || inicio.equals(fin)) {
            horarios.add(inicio);
            inicio = inicio.plusMinutes(30); // Incrementa en intervalos de 30 minutos
        }
        return horarios;
    }

    public List<LocalTime> obtenerHorasEntre(LocalTime horaInicio, LocalTime horaFin) {
        List<LocalTime> horasDisponibles = new ArrayList<>();
        LocalTime horaActual = horaInicio;

        // Mientras la hora actual sea antes de la hora de finalización
        while (!horaActual.isAfter(horaFin)) {
            horasDisponibles.add(horaActual);
            horaActual = horaActual.plusMinutes(30); // Puedes ajustar el intervalo según tus necesidades
        }

        return horasDisponibles;
    }

    public List<RangoHorario> obtenerRangosHorariosDisponiblesSegunFecha(List<RangoHorario> rangoHorarios, LocalDate fechaSeleccionada) {
        List<RangoHorario> rangosDisponibles = new ArrayList<>();

        for (RangoHorario rangoHorario : rangoHorarios) {
            LocalDate fechaRangoHorario = rangoHorario.getFecha();
            LocalTime horaInicio = rangoHorario.getHoraInicio();
            LocalTime horaFin = rangoHorario.getHoraFin();

            if (!fechaRangoHorario.isBefore(fechaSeleccionada)) {
                if (fechaRangoHorario.isEqual(fechaSeleccionada) || fechaRangoHorario.isAfter(fechaSeleccionada)) {
                    rangosDisponibles.add(rangoHorario);
                }
            }
        }

        return rangosDisponibles;
    }
}

