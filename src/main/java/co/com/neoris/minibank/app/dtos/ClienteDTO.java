package co.com.neoris.minibank.app.dtos;

import co.com.neoris.minibank.app.enums.EstadoCliente;
import co.com.neoris.minibank.app.enums.Genero;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ClienteDTO {

    private long clienteId;

    private String identificacion;

    private String nombres;

    private Genero genero;

    private int edad;

    private String direccion;

    private String telefono;

    private String contrasena;

    private EstadoCliente estado;

}
