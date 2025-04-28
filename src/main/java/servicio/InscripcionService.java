package servicio;

import JPA.Carrera;
import JPA.Estudiante;
import JPA.Inscripcion;
import jakarta.persistence.EntityManager;
import repository.EntityManagerFactory;
import repository.InscripcionRepository;

public class InscripcionService {
    private final InscripcionRepository inscripcionRepository = new InscripcionRepository();

    public Inscripcion matricularEstudiante(Estudiante estudiante, Carrera carrera, int anio) {
        // Acá se podrían agregar validaciones, como si ya está inscripto
        return inscripcionRepository.matricular(estudiante, carrera, anio);
    }

    public void borrarTodasLasInscripciones() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Inscripcion").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
