package DTO;



public class CarreraDTO {
    private Long id;
    private String nombre;
    private int cantidadInscriptos;


    public CarreraDTO() {
    }

    // Constructor con parámetros
    public CarreraDTO(Long id, String nombre, int cantidadInscriptos) {
        this.id = id;
        this.nombre = nombre;
        this.cantidadInscriptos = cantidadInscriptos;
    }

    // Getters y setters
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
        return "CarreraDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", cantidadInscriptos=" + cantidadInscriptos +
                '}';
    }
}
