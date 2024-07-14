package com.alura.foroHub.domain.respuestas;

import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.usuarios.Usuario;

import java.time.LocalDateTime;

public record DatosRetornarRespuestaSecundaria(Long id, Topico topico, String autorRespuestaPrincipal, String tituloRespuestaPrincipal, String mensajeRespuestaPrincipal, String tituloRespuestaSecundaria, String mensajeRespuestaSecundaria, LocalDateTime fechaRespuestaSecundaria) {


    public DatosRetornarRespuestaSecundaria(Topico topico, Usuario usuario, Respuesta respuestaPrincipal, Respuesta respuestaSecundaria) {

        this(respuestaSecundaria.getId(), topico, respuestaPrincipal.getAutor().getCorreoElectronico(), respuestaPrincipal.getMensaje(), respuestaPrincipal.getMensaje(), respuestaSecundaria.getMensaje(), respuestaSecundaria.getSolucion(), respuestaSecundaria.getFechaCreacion() );


    }
}
