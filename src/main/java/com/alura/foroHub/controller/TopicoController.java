package com.alura.foroHub.controller;


import com.alura.foroHub.domain.cursos.CursoRepository;
import com.alura.foroHub.domain.respuestas.DatosRespuesta;
import com.alura.foroHub.domain.respuestas.RespuestaRepository;
import com.alura.foroHub.domain.topicos.*;
import com.alura.foroHub.domain.usuarios.UsuarioRepository;
import com.alura.foroHub.infra.security.TokenService;
import com.alura.foroHub.infra.errores.TopicoNoExisteValidacion;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.ArrayList;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/topicos")
@CrossOrigin
@SecurityRequirement(name = "bearer-key")
public class TopicoController {


    @Autowired
    private CursoRepository cursoRepository;

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TokenService tokenService;


    @PostMapping
    public ResponseEntity registrarTopico (@RequestBody DatosRegistrarTopico datos, UriComponentsBuilder uriComponentsBuilder) {
//obtener curso
     var curso = cursoRepository.getReferenceById(datos.cursoId());
        System.out.println(curso.getNombre());
     //obtenr usuario que creo el post
     var autor = usuarioRepository.findByCorreoElectronico(datos.autor());
//crear Topico
     var topico = new Topico(datos.titulo(), datos.mensaje(), autor, curso);
     topico = topicoRepository.save(topico);
     var URI = uriComponentsBuilder.path("/topicos/{id}").buildAndExpand(topico.getId()).toUri();
     var datosRespuesta = new DatosRetornarTopico(topico, new ArrayList<DatosRespuesta>());
     return ResponseEntity.created(URI).body(datosRespuesta);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<Page<ListadoTopico>> obtenerTopicos (Pageable pageable) {


        var topicos = topicoRepository.obtenerTodosTopicos(pageable).map(t -> {

            var respuestas = respuestaRepository.obtenerRespuestas(t.getId());

            t.setRespuestas(respuestas);

            var datosRespuesta = respuestas.stream().map(r-> new DatosRespuesta(r)).toList();


          return  new ListadoTopico(t, datosRespuesta);
        } );


        return ResponseEntity.ok(topicos);



    }

    @GetMapping("/{id}")
    @Transactional
    public ResponseEntity obtenerTopicoEspecifico (@PathVariable Long id) {

        Topico topico = null;

        if(id == null) {
            throw new RuntimeException("Debe especificar un id");
        }

        var topicoSolicitado = topicoRepository.findById(id);

        if(!topicoSolicitado.isPresent() || !topicoSolicitado.get().getActivo()){
            throw new TopicoNoExisteValidacion("El topico Solicitado no existe");
        }

        topico = topicoSolicitado.get();

        var respuestas = respuestaRepository.obtenerRespuestas(topico.getId());

        topico.setRespuestas(respuestas);

        var datosRespuesta = respuestas.stream().map(r -> new DatosRespuesta(r)).toList();

        return ResponseEntity.ok(new DatosRetornarTopico(topico, datosRespuesta));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico (@PathVariable Long id, @RequestBody DatosActualizarTopico datosActualizar) throws IllegalAccessException {
        Topico topico = null;

        if(id == null) {
            throw new RuntimeException("Debe especificar un id");
        }
        try{
            topico = topicoRepository.getReferenceById(id);
        } catch (Exception e) {
            throw new RuntimeException("El id del topico solicitado no existe");
        }

        topico.actualizarTopico(datosActualizar);

        var datosRespuesta = respuestaRepository.obtenerRespuestas(topico.getId()).stream()
                .map(r -> new DatosRespuesta(r)).collect(Collectors.toList());

        return ResponseEntity.ok(new DatosRetornarTopico(topico, datosRespuesta ));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico (@PathVariable Long id, @RequestHeader("Authorization") String authToken) {

        Topico topico = null;


        if (id == null) {
            throw new RuntimeException("Debe especificar un id");
        }

        try {
            topico = topicoRepository.getReferenceById(id);
        } catch (Exception e) {
            throw new RuntimeException("El id del topico solicitado no existe");
        }

        var usuarioEliminando = tokenService.getSubject(authToken.replace("Bearer ", ""));

        if(!topico.getAutor().getUsername().equals(usuarioEliminando)){
            throw new TopicoNoExisteValidacion("No se puede eliminar un post de un usuario distinto");
        }

        topico.desactivarTopico();
        return ResponseEntity.noContent().build();

    }
}
