package co.com.neoris.minibank.app.services;

import co.com.neoris.minibank.app.dtos.CuentaDTO;
import co.com.neoris.minibank.app.dtos.MovimientoDTO;

import java.time.LocalDate;
import java.util.List;

public interface ICuenta {

    List<CuentaDTO> consultarPorCliente(String identificacion);

    CuentaDTO guardar(CuentaDTO cuentaDTO);

    void eliminar(long numeroDeCuenta);



}
