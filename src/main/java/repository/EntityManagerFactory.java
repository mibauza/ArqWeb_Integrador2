package repository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Persistence;

public class EntityManagerFactory {
    private static jakarta.persistence.EntityManagerFactory emf;

    // Método para inicializar el EntityManagerFactory (debe llamarse al inicio de la aplicación)
    public static void init() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("default");
        }
    }

    // Método para obtener un EntityManager
    public static EntityManager getEntityManager() {
        if (emf == null) {
            init();
        }
        return emf.createEntityManager();
    }

    // Método para cerrar el EntityManagerFactory (debe llamarse al finalizar la aplicación)
    public static void close() {
        if (emf != null && emf.isOpen()) {
            emf.close();
        }
    }
}
