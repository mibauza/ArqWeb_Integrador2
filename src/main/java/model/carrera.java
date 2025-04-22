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

public class carrera {
    @Id
    @Column(name = "id_carreara")
    private String nombre;
    //COMPLETAR LA REALACION
    @OneToMany
    private List<inscripcion> inscripciones;

}
