/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.Entidades.Imagen;
import com.mrhouse.mrhouse.enumeraciones.Rol;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioCliente;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thell
 */
@Service
public class ServicioCliente implements UserDetailsService {

    @Autowired
    private RepositorioCliente repositorioCliente;

    @Autowired
    private ServicioImagen servicioImagen;
    
    @Autowired
    private ServicioInmueble servicioInmueble;

    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String dni, String mail,
            String password, String password2, Rol rol) throws MiException {
        validar(nombre, mail, password, password2, dni);
        Cliente cliente = new Cliente();
        cliente.setNombre(nombre);
        cliente.setDni(dni);
        cliente.setEmail(mail);
        cliente.setPassword(new BCryptPasswordEncoder().encode(password));
        cliente.setRol(rol);
        Imagen imagen = servicioImagen.guardar(archivo);
        cliente.setImagen(imagen);
        repositorioCliente.save(cliente);

    }

    public void actualizar(MultipartFile archivo, String idCliente, String nombre, String mail,
            String password, String password2, String dni) throws MiException {
        validar(nombre, mail, password, password2,dni);
        Optional<Cliente> respuesta = repositorioCliente.findById(idCliente);
        if (respuesta.isPresent()) {
            Cliente cliente = respuesta.get();
            cliente.setEmail(mail);
            cliente.setPassword(new BCryptPasswordEncoder().encode(password));
            String idImagen = null;
            if (cliente.getImagen() != null) {
                idImagen = cliente.getImagen().getId();
            }
            Imagen imagen = servicioImagen.actualizar(archivo, idImagen);
            cliente.setImagen(imagen);
            repositorioCliente.save(cliente);
        }
    }

    public Cliente getOne(String id) {
        return repositorioCliente.getOne(id);
    }

    @Transactional
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = new ArrayList<>();
        clientes = repositorioCliente.findAll();
        return clientes;
    }
    
    @Transactional
    public void compra(Long idInmueble, String id){
        Inmueble inmueble = servicioInmueble.getOne(idInmueble);
        Cliente cliente = repositorioCliente.getOne(id);
        List<Inmueble> inmuebles = cliente.getInmueble();
        inmuebles.add(inmueble);
        cliente.setInmueble(inmuebles);
        
    }

    public void validar(String nombre, String email, String password, String password2, String dni) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("el nombre no puede estar vacio o ser nulo");
        }
        if (email == null || email.isEmpty()) {
            throw new MiException("el email no puede estar vacio o ser nulo");
        }
        if (password == null || password.isEmpty() || password.length() <= 5) {
            throw new MiException("el password no puede estar vacio o ser nulo y debe tener mas de 5 caracteres");
        }
        if (password2 == null || password2.isEmpty()) {
            throw new MiException("el password no puede estar vacio o ser nulo");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas deben ser iguales");
        }
        if(dni== null ){
            throw new MiException("el dni no puede estar vacio");}
    }

    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Cliente cliente = repositorioCliente.buscarPorEmail(mail);
        if (cliente != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + cliente.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = attr.getRequest().getSession(true);
            sesion.setAttribute("clientesession", cliente);
            return new User(cliente.getEmail(), cliente.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("no se encontro el cliente de el email " + mail);
        }
    }

}
