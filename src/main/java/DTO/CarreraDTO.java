package DTO;


import java.io.Serializable;

public class CarreraDTO implements Serializable {
    private Long id;
    private String nombre;
    private int cantidadInscriptos;
    private Integer duracion;

    public CarreraDTO() {
    }

    // Constructor con parámetros
    public CarreraDTO(Long id, String nombre, int cantidadInscriptos, Integer duracion) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadInscriptos = cantidadInscriptos;
        this.duracion = duracion;
    }

    // Getters y setters
    public Integer  getDuracion(){
        return duracion;
    }

    public void setDuracion(Integer  duracion){
        this.duracion=duracion;
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

    public int getCantidadInscriptos() {
        return cantidadInscriptos;
    }

    public void setCantidadInscriptos(int cantidadInscriptos) {
        this.cantidadInscriptos = cantidadInscriptos;
    }

    @Override
    public String toString() {
        return String.format("Carrera:\n" +
                        "---------\n" +
                        "Nombre: %s\n" +
                        "Cantidad de Inscriptos: %d",
                nombre, cantidadInscriptos);
    }
}
