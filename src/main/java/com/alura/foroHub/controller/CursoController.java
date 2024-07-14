package com.alura.foroHub.controller;

import com.alura.foroHub.domain.cursos.Curso;
import com.alura.foroHub.domain.cursos.CursoRepository;
import com.alura.foroHub.domain.cursos.DatosRegistrarCurso;
import com.alura.foroHub.domain.topicos.DatosRegistrarTopico;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cursos")
@SecurityRequirement(name = "bearer-key")
public class CursoController {

    @Autowired
    private CursoRepository cursoRepository;


    @PostMapping
    public ResponseEntity registrarCurso (@RequestBody DatosRegistrarCurso datos){

        var curso = new Curso(datos.nombre(), datos.categoria());

        cursoRepository.save(curso);
        return ResponseEntity.ok(curso);

    }
}
