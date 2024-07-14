package com.alura.foroHub.domain.respuestas;

import com.alura.foroHub.domain.respuestas.Respuesta;
import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record DatosRetornarRespuesta (Long id, String mensaje, String solucion, String autorRespuesta, LocalDateTime fechaCreacion, Long idTopico, String tituloTopico, String autorTopico ) {

    public DatosRetornarRespuesta (Respuesta respuesta, Topico topico) {
        this(respuesta.getId(), respuesta.getMensaje(), respuesta.getSolucion(), respuesta.getAutor().getCorreoElectronico(), respuesta.getFechaCreacion(), topico.getId(), topico.getTitulo(), topico.getAutor().getUsername());
    }



}
