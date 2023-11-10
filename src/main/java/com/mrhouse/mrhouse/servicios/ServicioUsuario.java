/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mrhouse.mrhouse.servicios;

import com.mrhouse.mrhouse.repositorios.RepositorioUsuario;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author thell
 */  
@Service
public class ServicioUsuario  implements UserDetailsService{
  

    @Autowired
    private RepositorioUsuario repositorioUsuario;
 
    @Transactional
    public void registrar(MultipartFile archivo, String nombre, String email, 
            String password, String password2) throws MiExcepcion{
        validar(nombre, email, password, password2);
        Usuario usuario =new Usuario();
        usuario.setNombre(nombre);
        usuario.setEmail(email);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(roles.USER);
   
        repositorioUsuario.save(usuario);
    }
      public void actualizar(MultipartFile archivo, String idUsuario, String nombre, String email, String password, String password2) throws Exception{
          validar(nombre, email, password, password2);
          Optional<Usuario> respuesta = repositorioUsuario.findById(idUsuario);
          if(respuesta.isPresent()){
              Usuario usuario = respuesta.get();
              usuario.setNombre(nombre);
              usuario.setEmail(email);
            usuario.setPassword(new BCryptPasswordEncoder().encode(password));
                  usuario.setRol(roles.USER);
                  String idImagen=null;
                  if(usuario.getImagen() != null){
                      idImagen=usuario.getImagen().getId();
                  }
                  Imagen imagen= imagenService.actualizar(archivo, idImagen);
                  usuario.setImagen(imagen);
       repositorioUsuario.save(usuario);
   
          }
          
          
      }
      
             public Usuario getOne(String id){
          
          return repositorioUsuario.getOne(id);
      }
              @Transactional()
    public List<Usuario> listarUsuarios() {

        List<Usuario> usuarios = new ArrayList();

        usuarios = repositorioUsuario.findAll();

        return usuarios;
    }
       @Transactional
    public void cambiarRol(String id){
        Optional<Usuario> respuesta = repositorioUsuario.findById(id);
    	
    	if(respuesta.isPresent()) {
    		
    		Usuario usuario = respuesta.get();
    		
    		if(usuario.getRol().equals(roles.USER)) {
    			
    		usuario.setRol(roles.ADMIN);
    		
    		}else if(usuario.getRol().equals(roles.ADMIN)) {
    			usuario.setRol(roles.USER);
    		}
    	}
    }
       public void validar( String nombre, String email, String password, String password2) throws MiExcepcion{
           if(nombre==null || nombre.isEmpty()){
               throw new MiExcepcion("el nombre no puede estar vacio o ser nulo");
           }
           if(email==null || email.isEmpty()){
                throw new MiExcepcion("el email no puede estar vacio o ser nulo");
           }
           if(password==null || password.isEmpty() || password.length()<=5){
                throw new MiExcepcion("el password no puede estar vacio o ser nulo y debe tener mas de 5 caracteres");
           }
           if(password2==null || password2.isEmpty()){
                  throw new MiExcepcion("el password no puede estar vacio o ser nulo");
           }
           if(!password.equals(password2)){
                  throw new MiExcepcion("Las contraseñas deben ser iguales");
           }
       }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
      Usuario usuario= repositorioUsuario.buscarPorEmail(email);
        if (usuario != null) {
            List<GrantedAuthority> permisos = new ArrayList<>();
            
            GrantedAuthority p = new SimpleGrantedAuthority("ROLE_"+ usuario.getRol().toString());
            
            permisos.add(p);
            
            ServletRequestAttributes attr =(ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
            HttpSession sesion =attr.getRequest().getSession(true);
            sesion.setAttribute("usuariosession", usuario);
      
      
              return new User(usuario.getEmail(), usuario.getPassword(), permisos);
        
                }
        else{
            throw new UsernameNotFoundException("no se encontro el usuario de el email "+ email);
        }
      
  
    }
}
