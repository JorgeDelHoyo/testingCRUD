package org.example.ej1y2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("persistencia");

    public static EntityManager getEntityManager(){
        return emf.createEntityManager();
    }

    public static void shutdown(){
        emf.close();
    }
}
