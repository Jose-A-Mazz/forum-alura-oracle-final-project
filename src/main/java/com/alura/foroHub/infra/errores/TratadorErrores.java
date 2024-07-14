package com.alura.foroHub.infra.errores;


import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class TratadorErrores {


    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity entidadNoencontrada (EntityNotFoundException e) {


        return ResponseEntity.badRequest().body("No se encontró el recurso solicitado");
    }

    @ExceptionHandler(TopicoNoExisteValidacion.class)
    public ResponseEntity topicoNoExiste (TopicoNoExisteValidacion e) {


        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
