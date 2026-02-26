package org.example.ej3;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class JPAUtil {
    private static EntityManagerFactory emf;

    public static EntityManager getEntityManager(){
        if(emf == null){
            emf = Persistence.createEntityManagerFactory("persistencia");
        }
        return emf.createEntityManager();
    }

    public static void shutdown(){
        emf.close();
    }
}
