package com.alura.foroHub.controller;


import com.alura.foroHub.domain.respuestas.*;
import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.topicos.TopicoRepository;
import com.alura.foroHub.domain.usuarios.BuscarUsuarioService;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.domain.usuarios.UsuarioRepository;
import com.alura.foroHub.infra.errores.TopicoNoExisteValidacion;
import com.alura.foroHub.infra.security.TokenService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/respuestas")
@SecurityRequirement(name = "bearer-key")
public class RespuestaController {

    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private BuscarUsuarioService buscarUsuarioService;


    @Autowired
    private RespuestaService respuestaService;


    @PostMapping
    @Operation(summary = "Metodo para registrar respuestas a topicos")
    public ResponseEntity registrarRespuesta (Pageable pageable, @RequestBody DatosRegistrarRespuesta datos, @RequestHeader("Authorization") String authToken, UriComponentsBuilder uriComponentsBuilder) {


        Topico topico = null;
        //obtener token

        var token = authToken.replace("Bearer ", "");
        var autor = usuarioRepository.findByCorreoElectronico(tokenService.getSubject(token));

        try{

 topico = topicoRepository.getReferenceById(datos.idTopico());
        } catch (Exception e) {
            throw new RuntimeException("El topico ingresado no existe");
        }

        var respuestaRegistrar = new Respuesta(datos, (Usuario) autor, topico);

        var respuesta = respuestaRepository.save(respuestaRegistrar);




        var url = uriComponentsBuilder.path("/respuestas?idRespuesta={id}").buildAndExpand(respuesta.getId()).toUri();


    return ResponseEntity.created(url).body(new DatosRetornarRespuesta(respuesta, topico));




    }

    //agregar una respuesta a una respuesta

    @Operation(summary = "Metodo para registrar respuestas de respuestas")
    @PostMapping("/{topicoId}/{respuestaId}")
    public ResponseEntity<?> responderRespuesta(
            @PathVariable Long topicoId,
            @PathVariable Long respuestaId,
            @RequestBody DatosRegistrarRespuesta datos,
            UriComponentsBuilder uriComponentsBuilder) {

        System.out.println("Se recibio la solicitud de responder Respuesta");

        if (topicoId == null) {
            throw new RuntimeException("Debe especificar un id para el topico que desea responder");
        }

        if (respuestaId == null) {
            throw new RuntimeException("Debe especificar el id de una respuesta. Si lo que desea es registrar una nueva respuesta, utilice el endpoint: /respuestas (POST)");
        }

        System.out.println("Buscando Respuesta padre/madre: " + respuestaId);
        Respuesta respuestaPrincipal = respuestaRepository.findById(respuestaId)
                .orElseThrow(() -> new RuntimeException("Respuesta no encontrada"));

        System.out.println("buscando el usuario autor del topico: " + topicoId);
        Usuario usuario = (Usuario) buscarUsuarioService.buscarUsuarioPorTopico(topicoId);

        System.out.println("Buscando el topico con el id: " + topicoId);
        Topico topico = topicoRepository.findById(topicoId)
                .orElseThrow(() -> new RuntimeException("Topico no encontrado"));

        System.out.println("Creando nueva respuesta");
        Respuesta respuestaSecundaria = new Respuesta(datos, usuario, topico, respuestaPrincipal);

        System.out.println("Guardando nueva respuesta");
        respuestaRepository.save(respuestaSecundaria);

        URI location = uriComponentsBuilder.path("/respuestas/{topicoId}/{respuestaId}")
                .buildAndExpand(topico.getId(), respuestaSecundaria.getId()).toUri();

        return ResponseEntity.created(location).body(new DatosRetornarRespuestaSecundaria(topico, usuario, respuestaPrincipal, respuestaSecundaria));
    }


    @GetMapping("/{topicoId}")
    @Transactional
    public ResponseEntity obtenerRespuestasTopico(@PathVariable Long topicoId) {
        Optional<Topico> topicoOptional = topicoRepository.findById(topicoId);

        if (!topicoOptional.isPresent()) {
            throw new TopicoNoExisteValidacion("El topico no existe. Intente con otro id!");
        }

        Topico topicoExistente = topicoOptional.get();

        // obtener Respuestas principales y agregarles respuestas secundarias
        List<ListadoRespuesta> listaRespuestas = respuestaRepository.obtenerRespuestasPrincipales(topicoExistente.getId()).stream()
                .map(respuesta -> {

                    //crearDTO
                    // Fetch sub-responses for each response
                    List<DatosRetornarRespuesta> listaSubRespuestas = respuestaService.buscarYAgregarSubRespuestas(respuesta.getId()).stream()
                            .map(sb-> new DatosRetornarRespuesta(sb, topicoExistente)).toList();
                    return new ListadoRespuesta(respuesta, listaSubRespuestas);
                })
                .toList();

        return ResponseEntity.ok(listaRespuestas);
    }
}
