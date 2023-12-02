package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.Entidades.Inmueble;
import com.mrhouse.mrhouse.Entidades.RangoHorario;
import com.mrhouse.mrhouse.repositorios.RepositorioRangoHorario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioRangoHorario {

    @Autowired
    private RepositorioRangoHorario repositorioRangoHorario;

    @Transactional
    public RangoHorario crearRangoHorario(LocalDate fecha, String diaSemana, LocalTime horaInicio, LocalTime horaFin, Inmueble inmueble) {
        RangoHorario rangoHorario = new RangoHorario();
        rangoHorario.setFecha(fecha);
        rangoHorario.setDiaSemana(diaSemana);
        rangoHorario.setHoraInicio(horaInicio);
        rangoHorario.setHoraFin(horaFin);
        rangoHorario.setInmueble(inmueble);
        return repositorioRangoHorario.save(rangoHorario);
    }

    public List<RangoHorario> establecerRangoHorarios(List<LocalDate> fechas, List<String> diaSemanaList, List<String> horaInicioList, List<String> horaFinList, Inmueble inmueble) {
        List<RangoHorario> rangosHorarios = new ArrayList<>();
        for (int i = 0; i < fechas.size(); i++) {
            LocalDate fecha = fechas.get(i);
            LocalTime horaInicio = LocalTime.parse(horaInicioList.get(i));
            LocalTime horaFin = LocalTime.parse(horaFinList.get(i));

            RangoHorario rangoHorario = crearRangoHorario(fecha, diaSemanaList.get(i), horaInicio, horaFin, inmueble);

            System.out.println("Asignando rangoHorario a Inmueble: " + rangoHorario);

            rangosHorarios.add(rangoHorario);
        }
        return rangosHorarios;
    }

    @Transactional
    public List<RangoHorario> actualizarRangoHorario(List<String> diaSemanaList, List<String> horaInicioList, List<String> horaFinList) {
        List<RangoHorario> rangosHorariosActualizados = new ArrayList<>();
        for (int i = 0; i < diaSemanaList.size(); i++) {
            RangoHorario rangoHorario = new RangoHorario();
            rangoHorario.setDiaSemana(diaSemanaList.get(i));
            rangoHorario.setHoraInicio(LocalTime.parse(horaInicioList.get(i)));
            rangoHorario.setHoraFin(LocalTime.parse(horaFinList.get(i)));
            rangosHorariosActualizados.add(repositorioRangoHorario.save(rangoHorario));
        }
        return rangosHorariosActualizados;
    }

    @Transactional
    public void eliminarInmueblePorId(String id) {
        // Implementa la lógica para eliminar un inmueble por su cuenta tributaria
        List<RangoHorario> rangosHorarios = (List<RangoHorario>) repositorioRangoHorario.findById(id);
        repositorioRangoHorario.deleteAll(rangosHorarios);
    }

    public RangoHorario obtenerRangoHorarioPorId(Long id) throws Exception {
        Optional<RangoHorario> rangoHorarioOptional = repositorioRangoHorario.findById(id);
        if (rangoHorarioOptional.isPresent()) {
            return rangoHorarioOptional.get();
        }
        throw new Exception("No se encontró el rango horario con ID: " + id);
    }

    public List<RangoHorario> obtenerRangoHorarioId(String id) throws Exception {
        return repositorioRangoHorario.findByCuentaTributaria(id);
    }

    public List<RangoHorario> obtenerRangosPorFecha(LocalDate fecha) {
        // Implementa la lógica según tus necesidades
        return repositorioRangoHorario.findByFecha(fecha);
    }

    public List<RangoHorario> obtenerTodosLosRangosHorarios() {
        return repositorioRangoHorario.findAll();
    }

    
}
