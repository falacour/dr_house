package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.Entidades.Cliente;
import com.mrhouse.mrhouse.Entidades.Ente;
import com.mrhouse.mrhouse.Entidades.Imagen;
import com.mrhouse.mrhouse.enumeraciones.Rol;
import com.mrhouse.mrhouse.excepciones.MiException;
import com.mrhouse.mrhouse.repositorios.RepositorioCliente;
import com.mrhouse.mrhouse.repositorios.RepositorioEnte;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ServicioEnte implements UserDetailsService{
    
    @Autowired
    private RepositorioEnte repositorioEnte;
    @Autowired
    private ServicioImagen servicioImagen;
    @Autowired
    private RepositorioCliente repositorioCliente;
    
    @Transactional()
    public void crearEnte(MultipartFile archivo, String nombre, String email, String password, String password2) throws MiException {
        validarDatos(nombre, email, password, password2);
        
        //validacion para saber si el email ya esta registrado
        validarEmailExistente(email);
        
        
        Ente ente = new Ente();
        
        ente.setEmail(email);
        ente.setNombre(nombre);
        ente.setPassword(new BCryptPasswordEncoder().encode(password));
        ente.setRol(Rol.ENTE);
        
        Imagen imagen = servicioImagen.guardar(archivo);
        ente.setImagen(imagen);
        
        repositorioEnte.save(ente);        
    }    
    
    @Transactional()
    public void modificarEnte(MultipartFile archivo, String id, String nombre, String email, String password, String password2) throws MiException {
        validarDatos(nombre, email, password, password2);
        
        Optional<Ente> respuesta = repositorioEnte.findById(id);        
        if (respuesta.isPresent()) {
            
            Ente ente = respuesta.get();
            if(!ente.getEmail().equals(email)){
                //validacion email en caso de que se modifique, sea unico
                validarEmailExistente(email);
            }
            ente.setEmail(email);
            ente.setNombre(nombre);
            ente.setPassword(password);
            ente.setRol(Rol.ENTE);
            
            Imagen imagen = servicioImagen.guardar(archivo);
            ente.setImagen(imagen);
            
            repositorioEnte.save(ente);
        }
              
    }
    
    public Ente getOne(String id){
        //version deprecada
        //Ente ente = repositorioEnte.getOne(id);
        Ente ente = repositorioEnte.getReferenceById(id);
        return ente;
    }

    public List<Ente> listarEntes(){
        List<Ente> lstEntes = repositorioEnte.findAll();
    
        return lstEntes;
    }
    
    private void validarDatos(String nombre, String email, String password, String password2) throws MiException {
        if (nombre == null || nombre.isEmpty()) {
            throw new MiException("El nombre del ente no puede ser nulo o estar vacio");
        }
        if (email == null || email.isEmpty()) {
            throw new MiException("El email del ente no puede ser nulo o estar vacio");
        }        
        if (password == null || password.isEmpty()) {
            throw new MiException("La contraseña del ente no puede ser nula o estar vacia");
        }
        if (password.length() < 6) {
            throw new MiException("La contraseña ingresada tiene menos de 6 caracteres");
        }
        if (!password.equals(password2)) {
            throw new MiException("Las contraseñas ingresadas deben ser iguales");
        }        
    }
    
    private void validarEmailExistente(String email) throws MiException {
        Ente ente = repositorioEnte.buscarPorEmail(email);
        if (ente != null) {
            throw new MiException("El email ya se encuentra registrado para otro ente");
        }
    }
    
    @Override
    public UserDetails loadUserByUsername(String mail) throws UsernameNotFoundException {
        Ente ente = repositorioEnte.buscarPorEmail(mail);
        if (ente != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + ente.getRol().toString());
            permisos.add(p);
            ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion = attr.getRequest().getSession(true);
            sesion.setAttribute("clientesession", ente);
            return new User(ente.getEmail(), ente.getPassword(), permisos);
        } else {
            throw new UsernameNotFoundException("no se encontro el cliente de el email " + mail);
        }
    }
    
}
