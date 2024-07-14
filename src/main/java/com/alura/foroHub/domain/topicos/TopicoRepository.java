package com.alura.foroHub.domain.topicos;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TopicoRepository extends JpaRepository<Topico, Long> {


    @Query("""
            SELECT t
            FROM Topico t
            WHERE t.activo = true
            ORDER BY t.fechaCreacion ASC
            """)
    Page<Topico> obtenerTodosTopicos (Pageable pageable);

}