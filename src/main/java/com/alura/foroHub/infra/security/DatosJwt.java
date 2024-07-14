package com.alura.foroHub.infra.security;

public record DatosJwt (String jwtToken) {

    public DatosJwt(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
