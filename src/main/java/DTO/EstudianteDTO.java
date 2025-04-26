package DTO;
import java.io.Serializable;
import java.util.List;

public class EstudianteDTO implements Serializable {
    private Long id;
    private String nombre;
    private String apellido;
    private int edad;
    private String genero;
    private String numeroDocumento;
    private String ciudadResidencia;
    private List<EstudianteCarreraDTO> carreras;

    public EstudianteDTO() { }

    public EstudianteDTO(Long id, String nombre, String apellido, List<EstudianteCarreraDTO> carreras) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
        this.carreras = carreras;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public List<EstudianteCarreraDTO> getCarreras() {
        return carreras;
    }

    public void setCarreras(List<EstudianteCarreraDTO> carreras) {
        this.carreras = carreras;
    }

    @Override
    public String toString() {
        return "EstudianteDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", carreras=" + carreras +
                '}';
    }
}
