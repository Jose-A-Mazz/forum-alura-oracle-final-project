package com.alura.foroHub.domain.topicos;


import com.alura.foroHub.domain.cursos.Curso;
import com.alura.foroHub.domain.cursos.CursoRepository;
import com.alura.foroHub.domain.respuestas.DatosRespuesta;
import com.alura.foroHub.domain.respuestas.Respuesta;
import com.alura.foroHub.domain.usuarios.Usuario;
import com.alura.foroHub.domain.usuarios.UsuarioRepository;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import org.hibernate.annotations.IdGeneratorType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Local;
import org.springframework.security.core.userdetails.UserDetails;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "topicos")
public class Topico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;

    private String mensaje;

    @Column(name = "fecha_creacion")
    private LocalDateTime fechaCreacion;

    @Enumerated(EnumType.STRING)
    private Status status;

    @OneToMany(mappedBy = "topico", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnore
    private List<Respuesta> respuestas = new ArrayList<>();


    @JoinColumn(name = "autor_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    @Embedded
    private Usuario autor;

    private Boolean activo;


    @JoinColumn(name="curso_id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonIgnore
    private Curso curso;

    public Topico(String titulo, String mensaje, UserDetails autor, Curso curso) {

        this.titulo = titulo;

        this.mensaje = mensaje;

        this.autor = (Usuario) autor;

        this.curso = curso;

        this.fechaCreacion = LocalDateTime.now();

        this.respuestas = null;

        this.status = Status.ABIERTO;
    }

    public Topico () {

    }

    public void desactivarTopico () {
        this.activo = false;
    }

    public void actualizarTopico (DatosActualizarTopico datos) throws IllegalAccessException {

        if(datos.titulo() != null){
            titulo = datos.titulo();
        }

        if(datos.mensaje() != null) {
            mensaje = datos.mensaje();
        }
    }


    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getMensaje() {
        return mensaje;
    }

    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }

    public Status getStatus() {
        return status;
    }

    public List<Respuesta> getRespuestas() {
        return respuestas;
    }

    public UserDetails getAutor() {
        return autor;
    }

    public Curso getCurso() {
        return curso;
    }


    public void setRespuestas(List<Respuesta> respuestas) {
        this.respuestas = respuestas;
    }

    public Boolean getActivo() {
        return activo;
    }
}
