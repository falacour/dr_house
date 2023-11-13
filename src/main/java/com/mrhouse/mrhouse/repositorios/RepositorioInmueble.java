/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.Inmueble;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author thell
 */
@Repository
public interface RepositorioInmueble extends JpaRepository<Inmueble, Long>{
    
       @Query("SELECT i FROM Inmueble i WHERE i.id = :id ")
    public Inmueble buscarPorEmail(@Param("id") Long id);
    
}
