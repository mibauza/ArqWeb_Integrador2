package servicio;

import JPA.Estudiante;
import repository.EstudianteRepository;
import jakarta.persistence.EntityManager; //Version nueva
import repository.EntityManagerFactory;


import java.util.List;

public class EstudianteService {
    private final EstudianteRepository estudianteRepository = new EstudianteRepository();

    public Estudiante altaEstudiante(Estudiante estudiante) {
        // Aquí podrías validar datos, verificar duplicados, etc.
        return estudianteRepository.save(estudiante);
    }

    public List<Estudiante> listarEstudiantes(String orden) {
        return estudianteRepository.findAll(orden);
    }

    public Estudiante buscarPorLibreta(String numeroLibreta) {
        return estudianteRepository.findByLibreta(numeroLibreta);
    }

    public List<Estudiante> buscarPorGenero(String genero) {
        return estudianteRepository.findByGenero(genero);
    }

    public List<Estudiante> buscarPorCarreraYCiudad(int idCarrera, String ciudad) {
        return estudianteRepository.findByCarreraAndCiudad(idCarrera, ciudad);
    }

    public Estudiante buscarPorDNI(String dni) {
        return estudianteRepository.findByDNI(dni);
    }

    public void borrarTodosLosEstudiantes() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Estudiante").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
