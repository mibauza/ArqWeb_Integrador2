package JPA;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "inscripciones")
public class Inscripcion {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "estudiante_id", nullable = false)
    private Estudiante estudiante;

    @ManyToOne
    @JoinColumn(name = "carrera_id", nullable = false)
    private Carrera carrera;

    @Temporal(TemporalType.DATE)
    @Column(name = "fecha_inscripcion")
    private Date fechaInscripcion;

    private int antiguedad;

    private boolean graduado;

    @Column(name = "anio_inscripcion")
    private Integer anioInscripcion;

    @Column(name = "anio_graduacion")
    private Integer anioGraduacion;

    // Constructor vacío requerido por JPA
    public Inscripcion() {
    }

    // Constructor con parámetros
    public Inscripcion(Estudiante estudiante, Carrera carrera, Date fechaInscripcion) {
        this.estudiante = estudiante;
        this.carrera = carrera;
        this.fechaInscripcion = fechaInscripcion;
        this.antiguedad = 0;
        this.graduado = false;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Estudiante getEstudiante() {
        return estudiante;
    }

    public void setEstudiante(Estudiante estudiante) {
        this.estudiante = estudiante;
    }

    public Carrera getCarrera() {
        return carrera;
    }

    public void setCarrera(Carrera carrera) {
        this.carrera = carrera;
    }

    public Date getFechaInscripcion() {
        return fechaInscripcion;
    }

    public void setFechaInscripcion(Date fechaInscripcion) {
        this.fechaInscripcion = fechaInscripcion;
    }

    public int getAntiguedad() {
        return antiguedad;
    }

    public void setAntiguedad(int antiguedad) {
        this.antiguedad = antiguedad;
    }

    public boolean isGraduado() {
        return graduado;
    }

    public void setGraduado(boolean graduado) {
        this.graduado = graduado;
    }

    public int getAnioInscripcion() {
        return this.anioInscripcion;
    }

    public void setAnioInscripcion(int anioInscripcion) {
        this.anioInscripcion = anioInscripcion;
    }

    @Override
    public String toString() {
        return "Inscripcion [id=" + id + ", estudiante=" + estudiante.getApellido() +
                ", carrera=" + carrera.getNombre() +
                ", fechaInscripcion=" + fechaInscripcion +
                ", graduado=" + graduado + "]";
    }

}
