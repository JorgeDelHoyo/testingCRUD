package org.example.ej3.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "libros")
public class Libro {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    @Column
    private String titulo;
    @Column
    private String isbn;
    @Column
    private double precio;
    @ManyToOne
    private Editorial editorial;
    @ManyToMany(mappedBy = "libros")
    private List<Autor> autores;

    public Libro() {}

    public Libro(String titulo, String isbn, double precio, Editorial editorial) {
        this.titulo = titulo;
        this.isbn = isbn;
        this.precio = precio;
        this.editorial = editorial;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    public Editorial getEditorial() {
        return editorial;
    }

    public void setEditorial(Editorial editorial) {
        this.editorial = editorial;
    }

    public List<Autor> getAutores() {
        return autores;
    }

    public void setAutores(List<Autor> autores) {
        this.autores = autores;
    }
}
