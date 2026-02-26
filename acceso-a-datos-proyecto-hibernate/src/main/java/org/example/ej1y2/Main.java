package org.example.ej1y2;


import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import org.example.ej1y2.model.Producto;

/**
 * Ejercicio 1 – Ciclo de vida de una entidad
 *
 * En este main se deben probar, en orden:
 *  - persist()
 *  - find()
 *  - modificación de entidad MANAGED
 *  - detach()
 *  - merge()
 *  - remove()
 *
 * Sigue los pasos y completa SOLO donde se indica.
 */
public class Main {
    public static void main(String[] args) {

        Long productoId;

        // persist(): transient → managed → commit
        productoId = crearProducto();

        //find() + modificación MANAGED  (sin usar merge)
        modificarProductoManaged(productoId);

        //detach(): Modificar una entidad DETACHED NO debe persistir
        //merge() Volver a asociar una entidad DETACHED
        probarDetach(productoId);

        //remove() Eliminar la entidad
        eliminarProducto(productoId);

        // Cierre final del EntityManagerFactory
        JPAUtil.shutdown();
    }

    /**
     * Crear un producto y persistirlo
     */
    private static Long crearProducto() {
        // Recuperamos de JPAUtil una instancia del entityManager
        EntityManager em = JPAUtil.getEntityManager();

        // Instanciamos una transacción
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            // Iniciamos una transacción

            /*
             *  - Crear un objeto Producto (estado TRANSIENT)
             *  - Mostrar por consola su estado
             */
            Producto producto = new Producto("productoNuevo", 5.3);
            System.out.println(producto);
            /*
             *  - Llamar a persist()
             *  - Mostrar por consola el producto (ya MANAGED)
             */
            em.persist(producto);
            System.out.println(producto);

            // Hacemos un commit()
            tx.commit();
            /*
             *  - Mostrar el ID generado
             *  - Devolver el ID
             */
            System.out.println("Producto creado con ID: " + producto.getId());

            return producto.getId();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close(); // al cerrar, la entidad pasa a DETACHED
        }
    }

    /**
     * find() + modificar estando MANAGED
     */
    private static void modificarProductoManaged(Long id) {
        // Inicializamos entityManager y la transacción
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            /*
             * TODO 4:
             *  - Recuperar el producto usando find()
             *  - Mostrarlo por consola
             */
            Producto producto = em.find(Producto.class, id);
            System.out.println(producto);

            /*
             * TODO 5:
             *  - Modificar el precio
             *  - NO usar merge()
             */
            producto.setPrecio(10.0);

            tx.commit();
            /*
             * TODO 6:
             *  - Indicar por consola que el cambio debe haberse guardado
             */
            System.out.println(producto);

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * detach()
     */
    private static void probarDetach(Long id) {
        // Inicializamos entityManager y la transacción
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();
        try {
            /*
             *  - Recuperar el producto con find()
             *  - Llamar a detach()
             */
            Producto producto = em.find(Producto.class, id);
            em.detach(producto);

            /*
             *  - Modificar el precio estando DETACHED
             *  - Mostrar por consola el nuevo valor
             */
            producto.setPrecio(15.0);
            System.out.println(producto.getPrecio());

            tx.commit();
            tx.begin();
            /*
             *  - Indicar que NO debería haberse guardado en BBDD
             */
            System.out.println("Producto modificado sin persistir");

            /*
             *  - Llamar a merge()
             *  - Guardar la referencia devuelta (entidad MANAGED)
             */
            Producto producto2 = em.merge(producto);

            tx.commit();
            /*
             * TODO 13:
             *  - Indicar que el cambio ahora SÍ debe persistirse
             */
            System.out.println("Producto modificado y persistido");
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }

    /**
     * remove()
     */
    private static void eliminarProducto(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        EntityTransaction tx = em.getTransaction();

        try {
            tx.begin();

            /*
             *  - Recuperar el producto con find()
             *  - Llamar a remove()
             */
            Producto producto = em.find(Producto.class, id);
            em.remove(producto);

            tx.commit();
            /*
             *  - Indicar que el producto debe haberse eliminado
             */
            System.out.println("Producto eliminado");

        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            throw e;
        } finally {
            em.close();
        }
    }
}
