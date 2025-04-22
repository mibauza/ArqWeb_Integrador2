package repository;

import model.carrera;
import model.estudiante;

import java.sql.Connection;

public class inscripcionRepository {
    private final Connection coneccion;

    public inscripcionRepository(Connection coneccion){
        this.coneccion = coneccion;
    }

    public int matricularEstudiante(int antiguedad, estudiante nuevoEstudiante, carrera nuevaCarrera) {

        return 1;
    }

}
