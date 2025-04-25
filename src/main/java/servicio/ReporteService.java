package servicio;

import DTO.ReporteCarreraDTO;
import repository.InscripcionRepository;

import java.util.List;

public class ReporteService {
    private final InscripcionRepository inscripcionRepository = new InscripcionRepository();

    public List<ReporteCarreraDTO> generarReporteCarreras() {
        return inscripcionRepository.getReporteCarreras();
    }
}
