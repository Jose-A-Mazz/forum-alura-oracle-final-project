package com.alura.foroHub.domain.topicos;

import com.alura.foroHub.domain.respuestas.DatosRespuesta;

import java.time.LocalDateTime;
import java.util.List;


public record DatosRetornarTopico(String titulo, String mensaje, LocalDateTime fechaDeCreacion, Status status, String nombreCurso, String autor, List<DatosRespuesta> datosRespuesta) {

        public DatosRetornarTopico(Topico topico, List<DatosRespuesta> respuestas){
            this(topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(), topico.getStatus(), topico.getCurso().getNombre(), topico.getAutor().getUsername(), respuestas);
        }


}

