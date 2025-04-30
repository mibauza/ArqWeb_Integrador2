package repository;

import JPA.Estudiante;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;


public class EstudianteRepository {

    // Método 2.a: Dar de alta un estudiante
    public Estudiante save(Estudiante estudiante) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(estudiante);
            em.getTransaction().commit();
            return estudiante;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Método para eliminar un estudiante
    public void delete(Estudiante estudiante) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(estudiante) ? estudiante : em.merge(estudiante));
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

    // Método 2.c: Recuperar todos los estudiantes con un criterio de ordenamiento
    public List<Estudiante> findAll(String orderBy) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT e FROM Estudiante e ORDER BY e." + orderBy;
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método 2.d: Recuperar un estudiante por su número de libreta
    public Estudiante findByLibreta(String numeroLibreta) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.numeroLibreta = :numeroLibreta";
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("numeroLibreta", numeroLibreta);
            return query.getSingleResult();
        } catch (Exception e) {
            return null;
        } finally {
            em.close();
        }
    }

    // Método 2.e: Recuperar estudiantes por género
    public List<Estudiante> findByGenero(String genero) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.genero = :genero";
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("genero", genero);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    // Método 2.g: Recuperar estudiantes por carrera y ciudad
    public List<Estudiante> findByCarreraAndCiudad(String nombreCarrera, String ciudad) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT DISTINCT e FROM Estudiante e JOIN e.inscripciones i " +
                    "WHERE i.carrera.nombre  = :nombreCarrera  AND e.ciudadResidencia = :ciudad";
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("nombreCarrera", nombreCarrera);
            query.setParameter("ciudad", ciudad);
            return query.getResultList();
        } finally {
            em.close();
        }
    }

    public Estudiante findByDNI(String dni) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT e FROM Estudiante e WHERE e.numeroDocumento = :dni";
            TypedQuery<Estudiante> query = em.createQuery(jpql, Estudiante.class);
            query.setParameter("dni", dni);
            return query.getSingleResult();
        } catch (Exception e) {
            return null; // Por si no encuentra
        } finally {
            em.close();
        }
    }

}
