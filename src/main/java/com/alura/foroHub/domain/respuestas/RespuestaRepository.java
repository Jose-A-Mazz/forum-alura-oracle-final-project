package com.alura.foroHub.domain.respuestas;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {



    @Query("SELECT r FROM Respuesta r WHERE r.topico.id = :topicoId")
    List<Respuesta> obtenerRespuestas (Long topicoId);


    @Query("select r from Respuesta r where r.respuestaPrincipal.id =:respuestaId")
    List<Respuesta> obtenerSubRespuestas (Long respuestaId);



    @Query("""
        select r from Respuesta r where r.topico.id = :topicoId
        and r.respuestaPrincipal is null
        """)
    List<Respuesta> obtenerRespuestasPrincipales (Long topicoId);

}
