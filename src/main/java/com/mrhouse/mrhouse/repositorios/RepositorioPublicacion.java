package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.Publicacion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPublicacion extends JpaRepository<Publicacion, String>{
    
}
