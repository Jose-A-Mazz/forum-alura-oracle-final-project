package com.alura.foroHub.domain.topicos;

import com.alura.foroHub.domain.cursos.Curso;
import com.alura.foroHub.domain.respuestas.Respuesta;
import com.alura.foroHub.domain.usuarios.Usuario;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

public record DatosRegistrarTopico (


                 String titulo,

                 String mensaje,

                 String autor,

                 Long cursoId

) {

}
