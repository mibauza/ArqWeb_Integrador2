package servicio;

import DTO.CarreraDTO;
import JPA.Carrera;
import repository.CarreraRepository;

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
}
