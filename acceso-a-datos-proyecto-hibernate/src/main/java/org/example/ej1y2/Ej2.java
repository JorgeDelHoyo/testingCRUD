package org.example.ej1y2;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.ej1y2.model.Perfil;
import org.example.ej1y2.model.Usuario;

public class Ej2 {
    public static void main(String[] args) {

        Long usuarioId = crearUsuarioConPerfil();

        leerUsuarioYAccederAlPerfil(usuarioId);

        probarAccesoConEntityManagerCerrado(usuarioId);

        borrarUsuarioYComprobarCascade(usuarioId);

        JPAUtil.shutdown();
    }

    /**
     * PASO A: Crear Usuario + Perfil y persistir SOLO el usuario.
     * - Si cascade incluye PERSIST, el perfil se persistirá automáticamente.
     */
    private static Long crearUsuarioConPerfil() {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            Usuario u = new Usuario("Ana", new Perfil("Bio de Ana", "600123123"));
            Perfil p = new Perfil("Bio de Ana", "600123123");
            Perfil pUni = new Perfil("Bio de Ana", "600123123");

            /*
             * TODO 1:
             *  - Asocia el perfil al usuario (lado dueño)
             *    u.setPerfil(p);
             */
            u.setPerfilUnidireccional(pUni);
            u.setPerfil(p);

            /*
             * TODO 2 (solo si implementas bidireccionalidad):
             *  - Mantén consistencia en memoria:
             *    p.setUsuario(u);
             */
            p.setUsuario(u);

            /*
             * TODO 3:
             *  - Persiste SOLO el usuario
             *    em.persist(u);
             */
            em.persist(u);

            tx.commit();

            System.out.println("[A] Usuario creado con id=" + u.getId());
            return u.getId();

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * PASO B: Borrar usuario y comprobar el efecto del cascade.
     * - Con cascade=PERSIST: se borra usuario, pero el perfil normalmente queda en la tabla perfiles.
     * - Con cascade=ALL o REMOVE: se borra usuario y su perfil.
     */
    private static void borrarUsuarioYComprobarCascade(Long usuarioId) {

        Long perfilId = null;

        // 1) Leer perfilId antes de borrar (para comprobar después)
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            Usuario u0 = em.find(Usuario.class, usuarioId);
            Perfil p0 = u0.getPerfil();

            /*
             * TODO 8:
             *  - Elimina el usuario con em.remove(u)
             */
            em.remove(u0);

            tx.commit();
            System.out.println("\n[D] Usuario eliminado (commit OK).");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * PASO C: Leer Usuario y acceder al Perfil.
     * - Con EAGER normalmente se carga inmediatamente.
     * - Con LAZY se carga al acceder (si EM sigue abierto).
     */
    private static void leerUsuarioYAccederAlPerfil(Long usuarioId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Usuario u = em.find(Usuario.class, usuarioId);
            System.out.println("\n[B] Usuario leído: " + u);

            /*
             * TODO 4:
             *  - Accede al perfil y muéstralo por consola
             *    System.out.println("[B] Perfil: " + u.getPerfil());
             */
            System.out.println("[B] Perfil: " + u.getPerfil());

        } finally {
            em.close();
        }
    }

    /**
     * PASO D: Probar acceso a la relación con el EntityManager cerrado.
     * - Si fetch=LAZY, es posible que salte una excepción al hacer u.getPerfil()
     *   fuera de sesión.
     */
    private static void probarAccesoConEntityManagerCerrado(Long usuarioId) {

        Usuario u;

        EntityManager em = JPAUtil.getEntityManager();
        try {
            u = em.find(Usuario.class, usuarioId);
            System.out.println("\n[C] Usuario cargado (aún con EM abierto): " + u);

            /*
             * TODO 5 (opcional):
             *  - Si quieres evitar la excepción en LAZY, fuerza la carga aquí:
             *    u.getPerfil();
             *  - Pero para “provocarla”, NO llames a getPerfil aquí.
             */
            u.getPerfil();

        } finally {
            em.close();
        }

        System.out.println("[C] EntityManager cerrado. Intentando acceder al perfil...");

        try {
            /*
             * TODO 6:
             *  - Accede al perfil con EM cerrado:
             *    System.out.println(u.getPerfil());
             */
            System.out.println(u.getPerfil());
        } catch (Exception ex) {
            System.out.println("[C] Excepción al acceder al perfil con EM cerrado:");
            System.out.println("    " + ex.getClass().getName() + " -> " + ex.getMessage());
        }
    }
}
