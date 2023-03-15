package co.com.neoris.minibank.app.entities;

import co.com.neoris.minibank.app.enums.Genero;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "personas")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
public class Persona {

    @Id
    @SequenceGenerator(name = "secuencia",sequenceName = "secuencia")
    @GeneratedValue(generator = "secuencia")
    @Column(name = "persona_id")
    private long personaId;

    @Column(unique = true)
    private String identificacion;

    private String nombres;

    @Enumerated(EnumType.STRING)
    private Genero genero;

    private int edad;

    private String direccion;

    private String telefono;

    public Persona(long personaId, String identificacion, String nombres, Genero genero, int edad, String direccion, String telefono) {
        this.personaId = personaId;
        this.identificacion = identificacion;
        this.nombres = nombres;
        this.genero = genero;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
    }
}
