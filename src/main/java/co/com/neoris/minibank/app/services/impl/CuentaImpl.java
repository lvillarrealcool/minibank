package co.com.neoris.minibank.app.services.impl;

import co.com.neoris.minibank.app.dtos.ClienteDTO;
import co.com.neoris.minibank.app.dtos.CuentaDTO;
import co.com.neoris.minibank.app.dtos.MovimientoDTO;
import co.com.neoris.minibank.app.entities.Cliente;
import co.com.neoris.minibank.app.entities.Cuenta;
import co.com.neoris.minibank.app.entities.Movimiento;
import co.com.neoris.minibank.app.enums.EstadoCuenta;
import co.com.neoris.minibank.app.enums.TipoDeCuenta;
import co.com.neoris.minibank.app.enums.TipoMovimiento;
import co.com.neoris.minibank.app.repository.ClienteRepositorio;
import co.com.neoris.minibank.app.repository.CuentaRepositorio;
import co.com.neoris.minibank.app.repository.MovimientoRepositorio;
import co.com.neoris.minibank.app.services.ICuenta;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CuentaImpl implements ICuenta {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Autowired
    private MovimientoRepositorio movimientoRepositorio;

    @Override
    public List<CuentaDTO> consultarPorCliente(String identificacion) {
        Cliente cliente=clienteRepositorio.findByIdentificacion(identificacion);
        List<Cuenta> cuentas=cuentaRepositorio.findByClienteAndEstadoCuenta(cliente, EstadoCuenta.True);
        final List<CuentaDTO> collect = cuentas.stream().map(cuenta -> CuentaDTO.builder()
                .estadoCuenta(cuenta.getEstadoCuenta().name())
                .cliente(ClienteDTO.builder().clienteId(cliente.getPersonaId())
                        .contrasena(cliente.getContrasena())
                        .estado(cliente.getEstado())
                        .identificacion(cliente.getIdentificacion())
                        .direccion(cliente.getDireccion())
                        .edad(cliente.getEdad())
                        .telefono(cliente.getTelefono())
                        .genero(cliente.getGenero())
                        .nombres(cliente.getNombres()).build())
                .cuentaId(cuenta.getCuentaId())
                .numeroDeCuenta(cuenta.getNumeroDeCuenta())
                .tipoCuenta(cuenta.getTipoDeCuenta().name())
                .saldoInicial(cuenta.getSaldoInicial())
                .build()).collect(Collectors.toList());
        return collect;
    }

    @Override
    public CuentaDTO guardar(CuentaDTO cuentaDTO) {
        Cliente cliente=clienteRepositorio.findByIdentificacion(cuentaDTO.getCliente().getIdentificacion());
        TipoDeCuenta tipoDeCuenta;

        if(cuentaDTO.getTipoCuenta().trim().equalsIgnoreCase(TipoDeCuenta.Corriente.name().toLowerCase())){
            tipoDeCuenta=TipoDeCuenta.Corriente;
        }else{
            tipoDeCuenta=TipoDeCuenta.Ahorro;
        }

        Cuenta cuenta = Cuenta.builder()
                .cliente(cliente)
                .estadoCuenta(EstadoCuenta.True)
                .numeroDeCuenta(cuentaDTO.getNumeroDeCuenta())
                .tipoDeCuenta(tipoDeCuenta)
                .saldoInicial(cuentaDTO.getSaldoInicial())
                .saldoDisponible(cuentaDTO.getSaldoInicial())
                .build();

        cuentaRepositorio.save(cuenta);

        return CuentaDTO.builder()
                .estadoCuenta(cuenta.getEstadoCuenta().name())
                .cliente(ClienteDTO.builder().clienteId(cliente.getPersonaId())
                        .contrasena(cliente.getContrasena())
                        .estado(cliente.getEstado())
                        .identificacion(cliente.getIdentificacion())
                        .direccion(cliente.getDireccion())
                        .edad(cliente.getEdad())
                        .telefono(cliente.getTelefono())
                        .genero(cliente.getGenero())
                        .nombres(cliente.getNombres()).build())
                .cuentaId(cuenta.getCuentaId())
                .numeroDeCuenta(cuenta.getNumeroDeCuenta())
                .tipoCuenta(cuenta.getTipoDeCuenta().name())
                .saldoInicial(cuenta.getSaldoInicial())
                .build();
    }

    @Override
    public void eliminar(long numeroDeCuenta) {
        Cuenta cuenta = cuentaRepositorio.findByNumeroDeCuenta(numeroDeCuenta);
        cuenta.setEstadoCuenta(EstadoCuenta.False);
        cuentaRepositorio.save(cuenta);
    }


}
