package org.example.ej3.service;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.ej3.JPAUtil;
import org.example.ej3.model.Autor;
import org.example.ej3.model.Editorial;
import org.example.ej3.model.Libro;
import org.example.ej3.repository.RepositoryAutor;
import org.example.ej3.repository.RepositoryEditorial;
import org.example.ej3.repository.RepositoryLibro;

import java.util.List;

public class Service {
    public Long crearDatosIniciales() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        Editorial editorial1 = new Editorial("Editorial 1", "España");
        RepositoryEditorial repositoryEditorial = new RepositoryEditorial();
        repositoryEditorial.persist(em, editorial1);
        tx.commit();
        em.close();

        Libro libro1 = new Libro("Libro 1", "123456", 10.3, editorial1);
        Libro libro2 = new Libro("Libro 2", "654321", 15.0, editorial1);
        Libro libro3 = new Libro("Libro 3", "987654", 12.5, editorial1);
        addLibro(editorial1.getId(), libro1);
        addLibro(editorial1.getId(), libro2);
        addLibro(editorial1.getId(), libro3);
        System.out.println(listaLibrosEditorial(editorial1.getId()));
        return editorial1.getId();
    }

    public void addLibro(Long idEditorial, Libro libro) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        RepositoryEditorial repositoryEditorial = new RepositoryEditorial();
        Editorial editorial = repositoryEditorial.findById(em, idEditorial);
        editorial.getLibros().add(libro);
        tx.commit();
        em.close();
    }

    public List<Libro> listaLibrosEditorial(Long idEditorial) {
        EntityManager em = JPAUtil.getEntityManager();
        RepositoryEditorial repositoryEditorial = new RepositoryEditorial();
        Editorial editorial = repositoryEditorial.findById(em, idEditorial);
        em.close();
        return editorial.getLibros();
    }

    public Libro buscarLibro(Long idLibro) {
        EntityManager em = JPAUtil.getEntityManager();
        RepositoryLibro repositoryLibro = new RepositoryLibro();
        return repositoryLibro.findById(em, idLibro);
    }

    public void addAutorALibro(Long autorId, Long libroId) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        RepositoryLibro repositoryLibro = new RepositoryLibro();
        RepositoryAutor repositoryAutor = new RepositoryAutor();
        Libro libro = repositoryLibro.findById(em, libroId);
        List<Autor> autores = libro.getAutores();
        autores.add(repositoryAutor.findById(em, autorId));
        libro.setAutores(autores);
        repositoryLibro.merge(em, libro);

        tx.commit();
        em.close();
    }

    public void addLibroConAutores(Long editorialId, Libro libro, List<Long> autoresIds) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        RepositoryEditorial repositoryEditorial = new RepositoryEditorial();
        Editorial editorial = repositoryEditorial.findById(em, editorialId);
        libro.setEditorial(editorial);
        RepositoryLibro repositoryLibro = new RepositoryLibro();
        repositoryLibro.persist(em, libro);
        tx.commit();
        em.close();
        autoresIds.forEach(autorId -> addAutorALibro(autorId, libro.getId()));
    }

    public void listarAutoresLibro(Long libroId) {
        EntityManager em = JPAUtil.getEntityManager();
        RepositoryLibro repositoryLibro = new RepositoryLibro();
        Libro libro = repositoryLibro.findById(em, libroId);
        System.out.println(libro.getAutores());
        em.close();
    }

    public void listarLibrosAutor(Long autorId) {
        EntityManager em = JPAUtil.getEntityManager();
        RepositoryAutor repositoryAutor = new RepositoryAutor();
        Autor autor = repositoryAutor.findById(em, autorId);
        System.out.println(autor.getLibros());
        em.close();
    }
}
