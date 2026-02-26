package org.example.ej1y2.model;

import jakarta.persistence.*;

@Entity
@Table(name = "perfiles")
public class Perfil {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String bio;
    @Column(nullable = false)
    private String telefono;
    @OneToOne(mappedBy = "perfil")
    private Usuario usuario;

    public Perfil() {}

    public Perfil(String bio, String telefono) {
        this.bio = bio;
        this.telefono = telefono;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getBio() {
        return bio;
    }
    public void setBio(String bio) {
        this.bio = bio;
    }
    public String getTelefono() {
        return telefono;
    }
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Override
    public String toString() {
        return "Perfil [id=" + id + ", bio=" + bio + ", telefono=" + telefono + "]";
    }
}
