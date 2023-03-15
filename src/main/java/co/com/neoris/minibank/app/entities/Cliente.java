package co.com.neoris.minibank.app.entities;

import co.com.neoris.minibank.app.enums.EstadoCliente;
import co.com.neoris.minibank.app.enums.Genero;
import lombok.Data;

import javax.persistence.*;

@Entity(name = "clientes")
@PrimaryKeyJoinColumn(referencedColumnName = "persona_id")
@Data
public class Cliente extends Persona{

    private String contrasena;

    @Enumerated(EnumType.STRING)
    private EstadoCliente estado;

    public Cliente(){
        super(0,"","",Genero.SinGenero,0,"","");
    }

    public Cliente(long personaId, String identificacion, String nombres, Genero genero, int edad, String direccion, String telefono, String contrasena, EstadoCliente estado) {
        super(personaId, identificacion, nombres, genero, edad, direccion, telefono);
        this.contrasena = contrasena;
        this.estado = estado;
    }
}
