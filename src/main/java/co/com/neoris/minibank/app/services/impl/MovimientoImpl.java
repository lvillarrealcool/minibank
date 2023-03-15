package co.com.neoris.minibank.app.services.impl;

import co.com.neoris.minibank.app.dtos.CuentaDTO;
import co.com.neoris.minibank.app.dtos.MovimientoDTO;
import co.com.neoris.minibank.app.entities.Cuenta;
import co.com.neoris.minibank.app.entities.Movimiento;
import co.com.neoris.minibank.app.enums.TipoMovimiento;
import co.com.neoris.minibank.app.exceptions.ApiResponseException;
import co.com.neoris.minibank.app.repository.CuentaRepositorio;
import co.com.neoris.minibank.app.repository.MovimientoRepositorio;
import co.com.neoris.minibank.app.services.IMovimientos;
import co.com.neoris.minibank.app.utils.Constantes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MovimientoImpl implements IMovimientos {

    @Autowired
    private CuentaRepositorio cuentaRepositorio;

    @Autowired
    private MovimientoRepositorio movimientoRepositorio;

    @Override
    public void credito(CuentaDTO cuentaDTO) {
        BigDecimal total = new BigDecimal(0);

        Cuenta cuenta = cuentaRepositorio.findByNumeroDeCuenta(cuentaDTO.getNumeroDeCuenta());
        total = total.add(cuenta.getSaldoDisponible()).add(cuentaDTO.getCredito());
        cuenta.setSaldoDisponible(total);

        Movimiento movimiento=Movimiento.builder()
                .fecha(Date.valueOf(LocalDate.now()))
                .tipoMovimiento(TipoMovimiento.Deposito)
                .valor(cuentaDTO.getCredito())
                .saldo(total)
                .movimiento(TipoMovimiento.Deposito.name().concat(" de ").concat(cuentaDTO.getCredito().toString()))
                .cuenta(cuenta)
                .build();

        movimientoRepositorio.save(movimiento);
        cuentaRepositorio.save(cuenta);
    }

    @Override
    public void debito(CuentaDTO cuentaDTO) {
        BigDecimal total = new BigDecimal(0);


        Cuenta cuenta = cuentaRepositorio.findByNumeroDeCuenta(cuentaDTO.getNumeroDeCuenta());

        if(BigDecimal.ZERO.equals(cuenta.getSaldoDisponible())){
            throw new ApiResponseException(Constantes.OOPS_SALDO_NO_DISPONIBLE);
        }

        total = total.add(cuenta.getSaldoDisponible()).subtract(cuentaDTO.getDebito());

        if(total.compareTo(BigDecimal.ZERO) < 0){
            throw new ApiResponseException(Constantes.OOPS_SALDO_NO_DISPONIBLE);
        }

        cuenta.setSaldoDisponible(total);

        Movimiento movimiento=Movimiento.builder()
                .fecha(Date.valueOf(LocalDate.now()))
                .tipoMovimiento(TipoMovimiento.Retiro)
                .valor(new BigDecimal(0).subtract(cuentaDTO.getDebito()))
                .saldo(total)
                .movimiento(TipoMovimiento.Retiro.name().concat(" de ").concat(cuentaDTO.getDebito().toString()))
                .cuenta(cuenta)
                .build();

        movimientoRepositorio.save(movimiento);
        cuentaRepositorio.save(cuenta);
    }

    @Override
    public List<MovimientoDTO> consultarMovimientosPorFechaYCliente(LocalDate desde, LocalDate hasta, String identificacion) {
        List<Movimiento> movimientos=movimientoRepositorio.consultarPorFechaYCliente(Date.valueOf(desde),Date.valueOf(hasta),identificacion);
        return movimientos.stream().map(m -> MovimientoDTO.builder()
                .fecha(m.getFecha().toLocalDate())
                .cliente(m.getCuenta().getCliente().getNombres())
                .numeroCuenta(String.valueOf(m.getCuenta().getNumeroDeCuenta()))
                .tipo(m.getCuenta().getTipoDeCuenta().name())
                .saldoIncial(m.getCuenta().getSaldoInicial())
                .estado(m.getCuenta().getEstadoCuenta().name().equalsIgnoreCase("true"))
                .movimiento(m.getValor())
                .saldoDisponible(m.getSaldo()).build()).collect(Collectors.toList());
    }

}
