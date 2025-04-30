package repository;

import DTO.CarreraDTO;
import JPA.Carrera;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.ArrayList;
import java.util.List;

public class CarreraRepository {

    // Método para guardar o actualizar una carrera
    public Carrera save(Carrera carrera) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();

            // Si la carrera no tiene ID, es nueva (persist)
            // Si ya tiene ID, se actualiza (merge)
            if (carrera.getId() == null || carrera.getId() == 0) {
                em.persist(carrera);
            } else {
                carrera = em.merge(carrera);
            }

            em.getTransaction().commit();
            return carrera;
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

    // Método para eliminar una carrera
    public void delete(Carrera carrera) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.remove(em.contains(carrera) ? carrera : em.merge(carrera));
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

    // Método para buscar una carrera por ID
    public Carrera findById(int id) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            return em.find(Carrera.class, id);
        } finally {
            em.close();
        }
    }

    // Método para listar todas las carreras
    public List<Carrera> findAll() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT c FROM Carrera c";
            TypedQuery<Carrera> query = em.createQuery(jpql, Carrera.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    public Carrera findByNombre(String nombre) {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT c FROM Carrera c WHERE LOWER(c.nombre) = LOWER(:nombre)";
            TypedQuery<Carrera> query = em.createQuery(jpql, Carrera.class);
            query.setParameter("nombre", nombre);

            List<Carrera> resultados = query.getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            em.close();
        }
    }

    // Método 2.f: Recuperar carreras con estudiantes inscriptos, ordenadas por cantidad
    public List<CarreraDTO> findAllWithEstudiantesCount() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            String jpql = "SELECT c, COUNT(i) as cantidad " +
                    "FROM Carrera c LEFT JOIN c.inscripciones i " +
                    "GROUP BY c " +
                    "HAVING COUNT(i) > 0 " +
                    "ORDER BY cantidad DESC";

            List<Object[]> results = em.createQuery(jpql).getResultList();
            List<CarreraDTO> carreras = new ArrayList<>();

            for (Object[] result : results) {
                Carrera carrera = (Carrera) result[0];
                Long cantidad = (Long) result[1];

                CarreraDTO dto = new CarreraDTO();
                dto.setId(carrera.getId());
                dto.setNombre(carrera.getNombre());
                dto.setCantidadInscriptos(cantidad.intValue());

                carreras.add(dto);
            }

            return carreras;
        } finally {
            em.close();
        }
    }
}
