package com.alura.foroHub.domain.respuestas;


import com.alura.foroHub.domain.topicos.Topico;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Entity
@Table(name = "respuestas")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Setter
public class Respuesta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Getter
    @ManyToOne
    @JoinColumn(name = "autor_id")
    @JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
    private Usuario autor;
    private String mensaje;


    //self-refernecing relationship. Se refiere a sí misma
    @ManyToOne
    //nombre de la columna que se utiliza para guardar el id de la respuesta madre/padre/principal
    @JoinColumn(name = "respuesta_principal")
    private Respuesta respuestaPrincipal;

    private String solucion;
    private LocalDateTime fechaCreacion;


    @ManyToOne
    @JoinColumn(name = "topico_id")
    private Topico topico;

    //el campo "respuestaPrincipal" es el dueño de la relacion
    //es el campo al que todas estas subRespuestas se refieren

    @OneToMany(mappedBy = "respuestaPrincipal", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Respuesta> subRespuestas = new ArrayList<>();

    public Respuesta(DatosRegistrarRespuesta datos, Usuario usuario, Topico topico) {

        this.autor = usuario;
        this.fechaCreacion = LocalDateTime.now();
        this.mensaje = datos.mensaje();
        this.solucion = datos.solucion();
        this.topico = topico;
        this.respuestaPrincipal = null;
        this.subRespuestas = new ArrayList<>();

    }

    public Respuesta(DatosRegistrarRespuesta datos, Usuario usuario, Topico topico, Respuesta respuestaPrincipal) {
        this.autor = usuario;
        this.fechaCreacion = LocalDateTime.now();
        this.mensaje = datos.mensaje();
        this.solucion = datos.solucion();
        this.topico = topico;
        this.respuestaPrincipal = respuestaPrincipal;
        this.subRespuestas = new ArrayList<Respuesta>();

    }

    public Long getId() {
        return id;
    }

    public String getMensaje() {
        return mensaje;
    }


    public Topico getTopico() {
        return topico;
    }


    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public String getSolucion() {
        return solucion;
    }


    public void setSubRespuestas(List<Respuesta> subRespuestas) {
        this.subRespuestas = subRespuestas;
    }
}
