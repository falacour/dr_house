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
    //Trae un inmueble por su id
    @Query("SELECT i FROM Inmueble i WHERE i.id = :id ")
    public Inmueble buscarPorid(@Param("id") Long id);
    //Trae una lista de inmuebles que pertenecen al ente selecionado
    @Query("SELECT i FROM Inmueble i WHERE i.ente.id = :id")
    public List inmueblesPorEnte(@Param("id") String id);
    //Trae una lista de inmuebles que pertenecen al ente seleccionado y no estan vendidos
    @Query("SELECT i FROM Inmueble i WHERE i.ente.id = :id AND i.cliente.id = null")
    public List inmueblesPorEnteAVender(@Param("id") String id);
    //Trae una lista de inmuebles que pertenecen al ente seleccionado y si estan vendidos
    @Query("SELECT i FROM Inmueble i WHERE i.ente.id = :id AND i.cliente.id != null")
    public List inmueblesPorEnteComprados(@Param("id") String id);
    //Trae una lista de inmuebles que pertenecen al cliente seleccionado
    @Query("SELECT i FROM Inmueble i WHERE i.cliente.id = :id")
    public List inmueblesPorCliente(@Param("id") String id);
    //Trae la lista de inmuebles que no estan comprados
    @Query("SELECT i FROM Inmueble i WHERE i.cliente.id = null")
    public List inmueblesNoComprados();
    //trae una lista de clientes del ente seleccionado
    @Query("SELECT i.cliente FROM Inmueble i WHERE i.ente.id = :id AND i.cliente.id != null")
    public List clientesDeEnte(@Param("id") String id);
    //trae todos los inmuebles que no estan comprados
    @Query("SELECT i FROM Inmueble i WHERE i.cliente = null")
    public List todosLosInmueblesAVender();
    //trae todos los inmuebles que si estan comprados
    @Query("SELECT i FROM Inmueble i WHERE i.cliente != null")
    public List todosLosInmueblesVendidos();

    //query para filtrar publicaciones
    @Query("SELECT i FROM Inmueble i "
            + "WHERE (:tipo IS NULL OR i.tipo = :tipo) "
            + "AND (:tipo IS NULL OR i.tipo = :tipo) "
            + "AND (:tipo IS NULL OR i.tipo = :tipo)")
    public List<Inmueble> buscarPorParametros(@Param("tipo") String tipo
    //            ,@Param("param2") String param2,
    //            @Param("param3") String param3
    );

}
