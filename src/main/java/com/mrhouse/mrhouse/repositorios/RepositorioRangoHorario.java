package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.RangoHorario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RepositorioRangoHorario extends JpaRepository<RangoHorario, Long> {

    @Query("SELECT r FROM RangoHorario r WHERE r.id = :id")
    List<RangoHorario> findById(@Param("id") String id);

    @Query("SELECT r FROM RangoHorario r WHERE r.inmueble.id = :id")
    List<RangoHorario> findByCuentaTributaria(@Param("id") String id);

    List<RangoHorario> findByFecha(@Param("fecha")LocalDate fecha);
}