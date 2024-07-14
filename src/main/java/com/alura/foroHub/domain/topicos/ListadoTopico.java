package com.alura.foroHub.domain.topicos;

import com.alura.foroHub.domain.respuestas.DatosRespuesta;
import com.alura.foroHub.domain.respuestas.Respuesta;

import java.time.LocalDateTime;
import java.util.List;

public record ListadoTopico (String titulo, String mensaje, String nombreCurso, LocalDateTime fechaCreacion, List<DatosRespuesta> datosRespuestas) {

    public ListadoTopico (Topico topico, List<DatosRespuesta> datosRespuesta) {
        this(topico.getTitulo(), topico.getMensaje(), topico.getCurso().getNombre(), topico.getFechaCreacion(), datosRespuesta );
    }
}
