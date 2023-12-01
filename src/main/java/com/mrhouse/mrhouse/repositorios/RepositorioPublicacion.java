package com.mrhouse.mrhouse.repositorios;

import com.mrhouse.mrhouse.Entidades.Publicacion;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RepositorioPublicacion extends JpaRepository<Publicacion, String> {

    //query para obtener todas las publicaciones de una persona, en este caso el receptor
    //ejemplo: soy el ADMIN y quiero ver todas las publicaciones/mensajes dirigidas a mi, usaria con mi id tomada del sesion
    @Query("SELECT p FROM Publicacion p WHERE p.receptor.id = :idReceptor")
    public List buscarTodasPorId(@Param("idReceptor") String idReceptor);

//    
//    //query para filtrar publicaciones
//    @Query("SELECT p FROM Publicacion p "
//            + "WHERE (:param1 IS NULL OR p.param1 = :param1) "
//            + "AND (:param2 IS NULL OR p.param2 = :param2) "
//            + "AND (:param3 IS NULL OR p.param3 = :param3)")
//    public List<Publicacion> buscarPorParametros(@Param("param1") String param1,
//            @Param("param2") String param2,
//            @Param("param3") String param3);

}
