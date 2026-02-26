package org.example.ej3.repository;

import jakarta.persistence.EntityManager;
import org.example.ej3.model.Libro;

public class RepositoryLibro implements BaseRepository<Libro, Long>{

    @Override
    public Libro findById(EntityManager em, Long id) {
        return em.find(Libro.class, id);
    }

    @Override
    public void persist(EntityManager em, Libro entity) {
        em.persist(entity);
    }

    @Override
    public Libro merge(EntityManager em, Libro entity) {
        return em.merge(entity);
    }

    @Override
    public void remove(EntityManager em, Libro entity) {
        em.remove(entity);
    }
}
