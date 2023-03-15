package co.com.neoris.minibank.app.services;

import co.com.neoris.minibank.app.dtos.CuentaDTO;
import co.com.neoris.minibank.app.dtos.MovimientoDTO;

import java.time.LocalDate;
import java.util.List;

public interface IMovimientos {

    void credito(CuentaDTO cuentaDTO);

    void debito(CuentaDTO cuentaDTO);

    List<MovimientoDTO> consultarMovimientosPorFechaYCliente(LocalDate desde, LocalDate hasta, String identificacion);

}
