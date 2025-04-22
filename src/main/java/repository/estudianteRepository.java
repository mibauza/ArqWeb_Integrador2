package repository;

import java.sql.Connection;

public class estudianteRepository {
    private final Connection coneccion;

    public estudianteRepository(Connection coneccion){
        this.coneccion = coneccion;
    }

    public int altaEstudiante(){
        return 1;
    }

}
