package JPA;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estudiantes")
public class Estudiante {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    private String apellido;

    private int edad;

    private String genero;

    @Column(name = "numero_documento", unique = true, nullable = false)
    private String numeroDocumento;

    @Column(name = "ciudad_residencia")
    private String ciudadResidencia;

    @Column(name = "numero_libreta", unique = true)
    private String numeroLibreta;

    @OneToMany(mappedBy = "estudiante")
    private List<Inscripcion> inscripciones = new ArrayList<>();

    // Constructor vacío requerido por JPA
    public Estudiante() {

    }

    // Constructor con parámetros
    public Estudiante(String nombre, String apellido, int edad, String genero, String numeroDocumento, String ciudadResidencia, String numeroLibreta) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.edad = edad;
        this.genero = genero;
        this.numeroDocumento = numeroDocumento;
        this.ciudadResidencia = ciudadResidencia;
        this.numeroLibreta = numeroLibreta;
    }

    // Getters y Setters
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

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCiudadResidencia() {
        return ciudadResidencia;
    }

    public void setCiudadResidencia(String ciudadResidencia) {
        this.ciudadResidencia = ciudadResidencia;
    }

    public String getNumeroLibreta() {
        return numeroLibreta;
    }

    public void setNumeroLibreta(String numeroLibreta) {
        this.numeroLibreta = numeroLibreta;
    }

    public List<Inscripcion> getInscripciones() {
        return inscripciones;
    }

    public void setInscripciones(List<Inscripcion> inscripciones) {
        this.inscripciones = inscripciones;
    }

    @Override
    public String toString() {
        return "\n📘 Estudiante:" +
                "\n----------------------------------" +
                "\n🆔 ID: " + id +
                "\n👤 Nombre completo: " + nombre + " " + apellido +
                "\n🧾 Documento: " + numeroDocumento +
                "\n📚 Libreta Universitaria: " + numeroLibreta +
                "\n🎂 Edad: " + edad +
                "\n⚧ Género: " + genero +
                "\n🏙️ Ciudad de residencia: " + ciudadResidencia +
                "\n----------------------------------";
    }
}
