package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.Entidades.Imagen;
import com.mrhouse.mrhouse.Entidades.Inmueble;
import com.mrhouse.mrhouse.Entidades.RangoHorario;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioCliente;
import org.springframework.beans.factory.annotation.Autowired;
import com.mrhouse.mrhouse.repositorios.RepositorioInmueble;
import com.mrhouse.mrhouse.repositorios.RepositorioRangoHorario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioInmueble {

    @Autowired
    private RepositorioInmueble repositorioInmueble;
    @Autowired
    private RepositorioCliente repositorioCliente;
    @Autowired
    private ServicioImagen servicioImagen;
    @Autowired
    private RepositorioRangoHorario repositorioRangoHorario;

    @Transactional
    public void crearInmueble(MultipartFile archivo, Long id, String tipo, Integer antiguedad, Long mts2,
            String direccion, Double precio, String provincia, String departamento, String descripcion,
            String idEnte, String transaccion, Integer hambientes) throws MiException {
        validar(id, tipo, antiguedad, mts2, direccion, precio, provincia, departamento,
                transaccion, hambientes);
        Inmueble inmueble = new Inmueble();
        inmueble.setTipo(tipo);
        inmueble.setAntiguedad(antiguedad);
        inmueble.setMts2(mts2);
        inmueble.setDireccion(direccion);
        inmueble.setPrecio(precio);
        inmueble.setProvincia(provincia);
        inmueble.setDepartamento(departamento);
        inmueble.setEnte(repositorioCliente.getOne(idEnte));
        if (descripcion != null) {
            inmueble.setDescripcion(descripcion);
        } else {
            inmueble.setDescripcion("");
        }
        inmueble.setAlta(Boolean.FALSE);
        inmueble.setHambientes(hambientes);
        inmueble.setTransaccion(transaccion);

        Imagen imagen = servicioImagen.guardar(archivo);

        inmueble.setImagen(imagen);
        repositorioInmueble.save(inmueble);

    }

    public List<Inmueble> listarInmuebles() {
        List<Inmueble> Inmuebles = new ArrayList<>();
        Inmuebles = repositorioInmueble.findAll();
        return Inmuebles;

    }

    public Inmueble obtenerInmueblePorId(Long id) {
        return repositorioInmueble.getOne(id);
    }

    //se deberia obtener el usuario de la session para poder llamar a este evento
    //en la navegacion previa a la visualizacion de la lista de los inmuebles del ente
    //Ej: si se mostrase la lista en la vista de perfil, obtener sesion al navegar a /perfil 
    //loadUserByUsername usuarioservicio
    // Ente ente = (Ente) session.getAttribute("entesession");
    public List<Inmueble> inmueblesPorEnte(String id) {
        List<Inmueble> lstInmuebles = repositorioInmueble.inmueblesPorEnte(id);
        return lstInmuebles;

    }

    public void modificar(MultipartFile archivo, Long id, String tipo, Integer antiguedad,
            Long mts2, String direccion, Double precio, String provincia, String departamento,
            String alta, Imagen imagen, String transaccion, Integer hambientes) throws MiException {

        validar(id, tipo, antiguedad, mts2, direccion, precio, provincia, departamento,
                transaccion, hambientes);

        Optional<Inmueble> respuesta = repositorioInmueble.findById(id);

        if (respuesta.isPresent()) {
            Inmueble inmueble = respuesta.get();
            inmueble.setTipo(tipo);
            inmueble.setAntiguedad(antiguedad);
            inmueble.setMts2(mts2);
            inmueble.setDireccion(direccion);
            inmueble.setPrecio(precio);
            inmueble.setProvincia(provincia);
            inmueble.setDepartamento(departamento);
            inmueble.setHambientes(hambientes);
            inmueble.setTransaccion(transaccion);

            if (alta.equals("dar de alta")) {
                inmueble.setAlta(Boolean.TRUE);
            } else {
                inmueble.setAlta(Boolean.FALSE);
            }
            repositorioInmueble.save(inmueble);

            System.out.println("NO HUBO ERROR EN LA MODIFICACION DEL iNMUEBLE");
        }
    }

    public Inmueble getOne(Long id) {
        return repositorioInmueble.getOne(id);

    }

    public void compra(String id, Long idInmueble) {
        Optional<Inmueble> respuesta = repositorioInmueble.findById(idInmueble);

        if (respuesta.isPresent()) {
            Inmueble inmueble = respuesta.get();
            inmueble.setCliente(repositorioCliente.getOne(id));
            repositorioInmueble.save(inmueble);
        }
    }

    public RangoHorario obtenerRangoHorarioPorId(Long id) throws Exception {
        Optional<RangoHorario> rangoHorarioOptional = repositorioRangoHorario.findById(id);
        if (rangoHorarioOptional.isPresent()) {
            return rangoHorarioOptional.get();
        }
        throw new Exception("No se encontró el rango horario con ID: " + id);
    }


    public void validar(Long id, String tipo, Integer antiguedad, Long mts2, String direccion,
            Double precio, String provincia, String departamento, String transaccion,
            Integer hambientes) throws MiException {

        if (id == null) {
            throw new MiException("falta el id");
        }
        if (tipo == null) {
            throw new MiException("el tipo no puede estar vacio o ser nulo");
        }
        if (antiguedad == null) {
            throw new MiException("ingrese la antiguedad");
        }
        if (mts2 == null) {
            throw new MiException("ingrese los metros cuadrados");
        }
        if (direccion == null || direccion.isEmpty()) {
            throw new MiException("el password no puede estar vacio o ser nulo");
        }
        if (precio == null || precio <= 0) {
            throw new MiException("el precio no puede ser nullo o igual a cero o menor");
        }
        if (provincia == null) {
            throw new MiException("la provincia no puede estar vacia");
        }
        if (departamento == null) {
            throw new MiException("el departamento no puede estar vacio");
        }
        if (transaccion == null || transaccion == "") {
            throw new MiException("la transaccion no puede estar vacia");
        }
        if (hambientes == null) {
            throw new MiException("los hambientes no puede estar vacio");
        }
    }

    public List<RangoHorario> obtenerRangosHorariosPorId(Long id) {
        Inmueble inmueble = getOne(id);

        // Si no se encuentra el inmueble, devolver una lista vacía o lanzar una excepción
        if (inmueble == null) {
            // Opción 1: Devolver una lista vacía
            // return new ArrayList<>();

            // Opción 2: Lanzar una excepción
            throw new EntityNotFoundException("Inmueble no encontrado con id: " + id);
        }

        // Devuelve los rangos horarios asociados al inmueble encontrado
        return inmueble.getRangosHorarios();
    }

}
