package com.alura.foroHub.domain.respuestas;


import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RespuestaService {

    @Autowired

    RespuestaRepository respuestaRepository;



    public List<Respuesta> buscarYAgregarSubRespuestas(Long idRespuesta) {
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(idRespuesta);

        if (!respuestaOptional.isPresent()) {
            throw new RuntimeException("La respuesta solicitada no existe. Intente de nuevo con otro id!");
        }

        Respuesta respuestaPrincipal = respuestaOptional.get();

        // Fetch sub-responses for the principal response
       return respuestaRepository.obtenerSubRespuestas(idRespuesta);

    }
}

