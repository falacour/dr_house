package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.Ente;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioEnte extends JpaRepository<Ente, String>{
    
    @Query("SELECT e FROM Ente e WHERE e.email = :email")
    public Ente buscarPorEmail(@Param("email") String email);
    
}
