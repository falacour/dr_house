package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.Cita;
import com.mrhouse.mrhouse.Entidades.RangoHorario;
import java.util.List;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface RepositorioCita extends CrudRepository<Cita, String> {

    @Query("SELECT r FROM RangoHorario r WHERE r.inmueble.id = :id")
    List<RangoHorario> findByCuentaTributaria(@Param("id") String id);

    @Query("SELECT c FROM Cita c WHERE c.horario = :rangoHorario")
    List<Cita> findByHorario(@Param("rangoHorario") RangoHorario rangoHorario);
}
