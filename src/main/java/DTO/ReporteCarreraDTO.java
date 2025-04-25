package DTO;

import java.util.List;

public class ReporteCarreraDTO {
    private Long id;
    private String nombre;
    private List<EstadisticasPorAnioDTO> estadisticasPorAnio;

    public ReporteCarreraDTO() { }

    public ReporteCarreraDTO(Long id, String nombre, List<EstadisticasPorAnioDTO> estadisticasPorAnio) {
        this.id = id;
        this.nombre = nombre;
        this.estadisticasPorAnio = estadisticasPorAnio;
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

    public List<EstadisticasPorAnioDTO> getEstadisticasPorAnio() {
        return estadisticasPorAnio;
    }

    public void setEstadisticasPorAnio(List<EstadisticasPorAnioDTO> estadisticasPorAnio) {
        this.estadisticasPorAnio = estadisticasPorAnio;
    }

    @Override
    public String toString() {
        return "ReporteCarreraDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estadisticasPorAnio=" + estadisticasPorAnio +
                '}';
    }
}
