package com.alura.foroHub.domain.respuestas;

import com.alura.foroHub.domain.topicos.Topico;

import java.time.LocalDateTime;
import java.util.List;

public record ListadoRespuesta (Long id, String autor, String mensaje, String solucion, LocalDateTime fechaCreacion, String tituloTopico, String mensajeTopico, List<DatosRetornarRespuesta> subRespuestas) {
    public ListadoRespuesta(Respuesta respuesta, List<DatosRetornarRespuesta> subRespuestas) {

        this(respuesta.getId(), respuesta.getAutor().getCorreoElectronico(), respuesta.getMensaje(), respuesta.getSolucion(), respuesta.getFechaCreacion(), respuesta.getTopico().getTitulo(), respuesta.getTopico().getMensaje(), subRespuestas);
    }
}
