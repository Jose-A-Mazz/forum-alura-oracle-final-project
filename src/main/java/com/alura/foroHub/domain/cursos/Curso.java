package com.alura.foroHub.domain.cursos;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cursos")
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Curso {

@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

private String nombre;


    @Enumerated(EnumType.STRING)
private Categoria categoria;

    public Curso(String nombre, Categoria categoria) {

        this.nombre = nombre;
        this.categoria = categoria;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }



    public Categoria getCategoria() {
        return categoria;
    }
}
