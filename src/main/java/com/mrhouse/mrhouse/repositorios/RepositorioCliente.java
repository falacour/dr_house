/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.Cliente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioCliente extends JpaRepository<Cliente, String> {

    @Query("SELECT c FROM Cliente c WHERE c.email = :email ")
    public Cliente buscarPorEmail(@Param("email") String email);
    
    @Query("SELECT c FROM Cliente c WHERE c.rol = 'CLIENTE' ")
    public List todosLosClientes();
    
    @Query("SELECT c FROM Cliente c WHERE c.rol = 'ENTE' ")
    public List todosLosEnte();
    
    @Query("SELECT c FROM Cliente c")
    public List todosLosUsuarios();
  
}
