package model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString

public class inscripcion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int numero_inscripcion;
    @Column
    private int antiguedad;
    @Column
    private boolean graduado;
    //COMPLETAR LA REALACION
    @ManyToOne
    private int documento_estudiante;
    private carrera carrera;

}
