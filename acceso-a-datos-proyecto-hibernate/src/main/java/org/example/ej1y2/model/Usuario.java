package org.example.ej1y2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String nombre;
    // Relacion bidireccional con perfil
    @OneToOne(mappedBy = "usuario", cascade = CascadeType.PERSIST)
    private Perfil perfil;
    // Relacion unidireccional con perfil, con Usuario como dueño
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "perfil_id")
    private Perfil perfilUnidireccional;

    public Usuario() {}

    public Usuario(String nombre, Perfil perfil, Perfil perfilUnidireccional) {
        this.nombre = nombre;
        this.perfil = perfil;
        this.perfilUnidireccional = perfilUnidireccional;
    }

    public Usuario(String nombre, Perfil perfilUnidireccional) {
        this.nombre = nombre;
        this.perfilUnidireccional = perfilUnidireccional;
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

    public Perfil getPerfil() {
        return perfil;
    }

    public void setPerfil(Perfil perfil) {
        this.perfil = perfil;
    }

    public Perfil getPerfilUnidireccional() {
        return perfilUnidireccional;
    }
    public void setPerfilUnidireccional(Perfil perfilUnidireccional) {
        this.perfilUnidireccional = perfilUnidireccional;
    }

    @Override
    public String toString() {
        return "Producto [id=" + id + ", nombre=" + nombre + ", perfil=" + perfil + "]";
    }
}
