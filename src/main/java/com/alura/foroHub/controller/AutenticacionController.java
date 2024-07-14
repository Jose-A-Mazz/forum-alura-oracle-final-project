package com.alura.foroHub.controller;

import com.alura.foroHub.domain.usuarios.DatosAutenticacionUsuario;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.infra.security.DatosJwt;
import com.alura.foroHub.infra.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
@CrossOrigin
public class AutenticacionController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity autenticarUsuario(@RequestBody DatosAutenticacionUsuario datosAutenticacionUsuario) {
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.nombreUsuario(),
                datosAutenticacionUsuario.clave());
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        return ResponseEntity.ok(new DatosJwt(JWTtoken));
    }   }

