package org.example.ej3.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "editoriales")
public class Editorial {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column
    String nombre;
    @Column
    String pais;
    @OneToMany(mappedBy = "editorial", fetch = FetchType.EAGER, cascade = CascadeType.PERSIST)
    private List<Libro> libros;

    public Editorial() {}

    public Editorial(String nombre, String pais) {
        this.nombre = nombre;
        this.pais = pais;
        libros = List.of();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    public String getPais() {
        return pais;
    }
    public void setPais(String pais) {}

    public List<Libro> getLibros() {
        return libros;
    }
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }
}
