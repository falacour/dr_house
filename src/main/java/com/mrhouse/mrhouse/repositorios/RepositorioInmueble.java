/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.Inmueble;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 *
 * @author thell
 */
@Repository
public interface RepositorioInmueble extends JpaRepository<Inmueble, Long> {

    @Query("SELECT i FROM Inmueble i WHERE i.id = :id ")
    public Inmueble buscarPorid(@Param("id") Long id);
    
    @Query("SELECT i FROM Inmueble i WHERE i.ente.id = :id")
    public List inmueblesPorEnte(@Param("id") String id);
    
    @Query("SELECT i FROM Inmueble i WHERE i.ente.id = :id AND i.cliente.id = null")
    public List inmueblesPorEnteVender(@Param("id") String id);
    
    @Query("SELECT i FROM Inmueble i WHERE i.ente.id = :id AND i.cliente.id != null")
    public List inmueblesPorEnteComprados(@Param("id") String id);
    
    @Query("SELECT i FROM Inmueble i WHERE i.cliente.id = :id")
    public List inmueblesPorCliente(@Param("id") String id);
    
    @Query("SELECT i FROM Inmueble i WHERE i.cliente.id = null")
    public List inmueblesNoComprados();
    
    @Query("SELECT i.cliente FROM Inmueble i WHERE i.ente.id = :id AND i.cliente.id != null")
    public List clientesDeEnte(@Param("id") String id);

}
