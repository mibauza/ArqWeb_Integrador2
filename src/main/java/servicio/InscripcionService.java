package servicio;

import JPA.Carrera;
import JPA.Estudiante;
import JPA.Inscripcion;
import repository.InscripcionRepository;

public class InscripcionService {
    private final InscripcionRepository inscripcionRepository = new InscripcionRepository();

    public Inscripcion matricularEstudiante(Estudiante estudiante, Carrera carrera, int anio) {
        // Acá se podrían agregar validaciones, como si ya está inscripto
        return inscripcionRepository.matricular(estudiante, carrera, anio);
    }
}
