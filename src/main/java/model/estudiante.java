package model;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class estudiante {

    @Id
    @Column(name= "id_estudiante")
    private int documento;
    @Column
    private String nombre;
    @Column
    private String apellido;
    @Column
    private int edad;
    @Column
    private String genero;
    @Column
    private String ciudadResidencia;
    @Column
    private int libretaUniversitaria;
    //COMPLETAR LA REALACION
    private List<inscripcion> inscripcion;

}
