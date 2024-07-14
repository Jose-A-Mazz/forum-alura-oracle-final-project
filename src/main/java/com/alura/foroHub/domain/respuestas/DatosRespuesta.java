package com.alura.foroHub.domain.respuestas;

import java.time.LocalDateTime;

public record DatosRespuesta (Long id, String mensaje, String solucion, String autorRespuesta, LocalDateTime fechaCreacion) {

    public DatosRespuesta(Respuesta r) {
        this(r.getId(), r.getMensaje(), r.getSolucion(), r.getAutor().getCorreoElectronico(), r.getFechaCreacion());
    }
}
