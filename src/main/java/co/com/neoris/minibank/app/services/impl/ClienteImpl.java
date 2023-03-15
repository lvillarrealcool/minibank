package co.com.neoris.minibank.app.services.impl;

import co.com.neoris.minibank.app.dtos.ClienteDTO;
import co.com.neoris.minibank.app.entities.Cliente;
import co.com.neoris.minibank.app.repository.ClienteRepositorio;
import co.com.neoris.minibank.app.services.ICliente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClienteImpl implements ICliente {

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public ClienteDTO guardar(ClienteDTO clienteDTO) {

        Cliente cliente = new Cliente(clienteDTO.getClienteId(),
                clienteDTO.getIdentificacion(),
                clienteDTO.getNombres(),
                clienteDTO.getGenero(),
                clienteDTO.getEdad(),
                clienteDTO.getDireccion(),
                clienteDTO.getTelefono(),
                clienteDTO.getContrasena(),
                clienteDTO.getEstado());

        clienteRepositorio.save(cliente);

        clienteDTO.setClienteId(cliente.getPersonaId());

        return clienteDTO;

    }

    @Override
    public ClienteDTO buscarPorIdentificacion(String identificacion) {
        Cliente cliente = clienteRepositorio.findByIdentificacion(identificacion);

        return ClienteDTO.builder()
                .clienteId(cliente.getPersonaId())
                .contrasena(cliente.getContrasena())
                .estado(cliente.getEstado())
                .identificacion(cliente.getIdentificacion())
                .direccion(cliente.getDireccion())
                .edad(cliente.getEdad())
                .telefono(cliente.getTelefono())
                .genero(cliente.getGenero())
                .nombres(cliente.getNombres()).build();
    }

    @Override
    public void eliminarPorIdentificacion(String identificacion) {
        Cliente cliente=clienteRepositorio.findByIdentificacion(identificacion);
        clienteRepositorio.deleteById(cliente.getPersonaId());
    }

    @Override
    public List<ClienteDTO> consultarTodos() {
        List<Cliente> clientes = (List<Cliente>) clienteRepositorio.findAll();

        return clientes.stream().map(c-> ClienteDTO.builder()
                .clienteId(c.getPersonaId())
                .contrasena(c.getContrasena())
                .estado(c.getEstado())
                .identificacion(c.getIdentificacion())
                .direccion(c.getDireccion())
                .edad(c.getEdad())
                .telefono(c.getTelefono())
                .genero(c.getGenero())
                .nombres(c.getNombres()).build()).collect(Collectors.toList());

    }
}
