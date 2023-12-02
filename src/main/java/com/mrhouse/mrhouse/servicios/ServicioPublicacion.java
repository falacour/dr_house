package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.Entidades.Publicacion;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioCliente;
import com.mrhouse.mrhouse.repositorios.RepositorioPublicacion;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ServicioPublicacion {

    @Autowired
    RepositorioPublicacion repositorioPublicacion;
    @Autowired
    RepositorioCliente repositorioCliente;

    @Transactional
    public void crearPublicacion(String asunto, String mensaje, String idEmisor, String idReceptor) throws MiException {
        validar(asunto, mensaje, idEmisor, idReceptor);

        Publicacion publicacion = new Publicacion();
        Cliente emisor = repositorioCliente.getReferenceById(idEmisor);
        Cliente receptor = repositorioCliente.getReferenceById(idReceptor);

        publicacion.setAsunto(asunto);
        publicacion.setMensaje(mensaje);
        //publicacion.setEmisor(emisor);
        //publicacion.setReceptor(receptor);
        //publicacion.setLeido(false);
        //publicacion.setEstado(true);

        repositorioPublicacion.save(publicacion);

    }

    @Transactional
    public void modificarPublicacion(String idPublicacion, String asunto, String mensaje, String idEmisor, String idReceptor) throws MiException {
        validar(asunto, mensaje, idEmisor, idReceptor);

        Optional<Publicacion> respuestaPubli = repositorioPublicacion.findById(idPublicacion);
        Optional<Cliente> respuestaEmisor = repositorioCliente.findById(idEmisor);
        Optional<Cliente> respuestaReceptor = repositorioCliente.findById(idReceptor);

        Cliente emisor = new Cliente();
        Cliente receptor = new Cliente();

        if (respuestaEmisor.isPresent()) {
            emisor = respuestaEmisor.get();
        }

        if (respuestaReceptor.isPresent()) {
            emisor = respuestaReceptor.get();
        }

        if (respuestaPubli.isPresent()) {
            Publicacion publicacion = respuestaPubli.get();
            publicacion.setAsunto(asunto);
            publicacion.setMensaje(mensaje);
            publicacion.setEmisor(emisor);
            publicacion.setReceptor(receptor);

            repositorioPublicacion.save(publicacion);
        }
    }
    
    public List listarPublicaciones(){
       return  repositorioPublicacion.findAll();   
    }
    
    public Publicacion getReferenceById(String id){
        return repositorioPublicacion.getReferenceById(id);
    }
    @Transactional
    public void marcarComoLeido(String id){   
        Optional<Publicacion> respuesta = repositorioPublicacion.findById(id);
        if (respuesta.isPresent()) {
            Publicacion publicacion = respuesta.get();
            publicacion.setLeido(true);
            
            repositorioPublicacion.save(publicacion);
        }   
    }
    
    //listar todas las publicaciones de un cliente en especifico
    public List listarPorIdReceptor(String id){
       return  repositorioPublicacion.buscarTodasPorIdReceptor(id);   
    }
    
    //listar todas las publicaciones de un cliente en especifico
    public List listarPorIdEmisor(String id){
       return  repositorioPublicacion.buscarTodasPorIdEmisor(id);   
    }

    private void validar(String asunto, String mensaje, String idEmisor, String idReceptor) throws MiException {
        if (asunto == null || asunto.isEmpty()) {
            throw new MiException("El asunto no puede ser nulo ni estar vacio");
        }
        if (mensaje == null || mensaje.isEmpty()) {
            throw new MiException("El mensaje no puede ser nulo ni estar vacio");
        }

        if (idEmisor == null || idEmisor.isEmpty()) {
            throw new MiException("El id del emisor no puede ser nulo ni estar vacio");
        }
        if (idReceptor == null || idReceptor.isEmpty()) {
            throw new MiException("El id del receptor no puede ser nulo ni estar vacio");
        }
    }   

}
