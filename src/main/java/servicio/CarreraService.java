package servicio;

import DTO.CarreraDTO;
import JPA.Carrera;
import jakarta.persistence.EntityManager;
import repository.CarreraRepository;
import repository.EntityManagerFactory;

import java.util.List;

public class CarreraService {
    private final CarreraRepository carreraRepository = new CarreraRepository();

    public Carrera guardarCarrera(Carrera carrera) {
        return carreraRepository.save(carrera);
    }

    public void eliminarCarrera(Carrera carrera) {
        carreraRepository.delete(carrera);
    }

    public Carrera buscarCarreraPorId(int id) {
        return carreraRepository.findById(id);
    }

    public List<Carrera> listarCarreras() {
        return carreraRepository.findAll();
    }

    public List<CarreraDTO> carrerasConEstudiantes() {
        return carreraRepository.findAllWithEstudiantesCount();
    }

    public void borrarTodasLasCarreras() {
        EntityManager em = EntityManagerFactory.getEntityManager();
        try {
            em.getTransaction().begin();
            em.createQuery("DELETE FROM Carrera").executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
