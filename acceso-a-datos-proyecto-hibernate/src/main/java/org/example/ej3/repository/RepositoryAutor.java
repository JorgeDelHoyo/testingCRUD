package org.example.ej3.repository;

import jakarta.persistence.EntityManager;
import org.example.ej3.model.Autor;

public class RepositoryAutor implements BaseRepository<Autor, Long>{

    @Override
    public Autor findById(EntityManager em, Long id) {
        return em.find(Autor.class, id);
    }

    @Override
    public void persist(EntityManager em, Autor entity) {
        em.persist(entity);
    }

    @Override
    public Autor merge(EntityManager em, Autor entity) {
        return em.merge(entity);
    }

    @Override
    public void remove(EntityManager em, Autor entity) {
        em.remove(entity);
    }
}
