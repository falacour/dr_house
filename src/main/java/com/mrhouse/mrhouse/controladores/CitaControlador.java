package com.mrhouse.mrhouse.controladores;

import com.mrhouse.mrhouse.Entidades.FechaHoraContainer;
import com.mrhouse.mrhouse.Entidades.Inmueble;
import com.mrhouse.mrhouse.Entidades.RangoHorario;
import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import com.mrhouse.mrhouse.servicios.ServicioCita;
import com.mrhouse.mrhouse.servicios.ServicioInmueble;
import com.mrhouse.mrhouse.servicios.ServicioRangoHorario;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/cita")
public class CitaControlador {

    @Autowired
    private ServicioCita servicioCita;

    @Autowired
    private ServicioRangoHorario servicioRangoHorario;

    @Autowired
    private ServicioInmueble servicioInmueble;

    @Autowired
    private RepositorioInmueble repositorioInmueble;
    
    @GetMapping("/registrar/{cuentaTributaria}")
    public String registrarCita(@PathVariable("cuentaTributaria") String id,
                                ModelMap model, HttpSession session) throws Exception {
        Inmueble inmueble = servicioInmueble.obtenerInmueblePorId(id);
        List<RangoHorario> rangoHorario = servicioRangoHorario.obtenerRangoHorarioPorId(id);
        Cliente cliente = (Cliente) session.getAttribute("usuariosession");
        model.put("inmueble", inmueble);

        // Cambia la clave de r.getId() a r.getFecha()
        Map<String, List<LocalTime>> horariosDisponiblesMap = new HashMap<>();
        for (RangoHorario r : rangoHorario) {
            List<LocalTime> horariosDisponibles = servicioCita.obtenerHorariosDisponibles(r.getHoraInicio(), r.getHoraFin());
            String fechaFormateada = r.getFecha().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            horariosDisponiblesMap.put(fechaFormateada, horariosDisponibles);
        }

        model.put("horariosDisponiblesMap", horariosDisponiblesMap);
        model.put("rangoHorario", rangoHorario);
        model.put("cliente", cliente);
        return "cita_form.html";
    }

    @PostMapping("/registrar/{cuentaTributaria}")
    public String registrarCita(@RequestParam String idEnte, @RequestParam String idCliente,
                                @RequestParam Long idHorario, @RequestParam(required = false) String nota,
                                ModelMap modelo) {
        System.out.println("HOLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA");
        try {
            System.out.println("OOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO");
            System.out.println("Id del ente: " + idEnte);
            System.out.println("Id del CLiente: " + idCliente);
            System.out.println("Id del Horario: " + idHorario);
            servicioCita.crearCita(idEnte, idCliente, idHorario, nota);
            modelo.put("exito", "la cita fue cargada correctamente");
        } catch (Exception e) {
            System.out.println(e);
            System.out.println("El id del horario es: " + idHorario);
            return "cita_form";
        }

        return "redirect:/";
    }

    @GetMapping("/horarios-disponibles/{cuentaTributaria}")
    @ResponseBody
    public ResponseEntity<Map<String, List<LocalTime>>> obtenerHorariosDisponibles(@PathVariable String cuentaTributaria) {
        try {
            List<RangoHorario> rangoHorarioList = servicioInmueble.obtenerRangoHorariosPorId(id);

            if (rangoHorarioList.isEmpty()) {
                return ResponseEntity.notFound().build();
            }

            // Formatea las fechas como cadenas en el formato "yyyy-MM-dd"
           Map<String, List<LocalTime>> horariosPorFecha = rangoHorarioList.stream()
                    .collect(Collectors.groupingBy(
                            rango -> rango.getFecha().toString(),
                            Collectors.flatMapping(
                                    rango -> {
                                        List<LocalTime> horas = new ArrayList<>();
                                        LocalTime horaInicio = rango.getHoraInicio();
                                        LocalTime horaFin = rango.getHoraFin();

                                        while (horaInicio.isBefore(horaFin) || horaInicio.equals(horaFin)) {
                                            horas.add(horaInicio);
                                            horaInicio = horaInicio.plusMinutes(30);
                                        }

                                        return horas.stream();
                                    },
                                    Collectors.toList()
                            )
                    ));

            return ResponseEntity.ok(horariosPorFecha);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

}
