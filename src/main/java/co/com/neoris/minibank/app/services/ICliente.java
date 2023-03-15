package co.com.neoris.minibank.app.services;

import co.com.neoris.minibank.app.dtos.ClienteDTO;

import java.util.List;

public interface ICliente {

    ClienteDTO guardar(ClienteDTO clienteDTO);

    ClienteDTO buscarPorIdentificacion(String identificacion);

    void eliminarPorIdentificacion(String identificacion);

    List<ClienteDTO> consultarTodos();

}
