package org.example.ej3.repository;

import jakarta.persistence.EntityManager;
import org.example.ej3.model.Editorial;

public class RepositoryEditorial implements BaseRepository<Editorial, Long> {

    @Override
    public Editorial findById(EntityManager em, Long id) {
        return em.find(Editorial.class, id);
    }

    @Override
    public void persist(EntityManager em, Editorial entity) {
        em.persist(entity);
    }

    @Override
    public Editorial merge(EntityManager em, Editorial entity) {
        return em.merge(entity);
    }

    @Override
    public void remove(EntityManager em, Editorial entity) {
        em.remove(entity);
    }
}
