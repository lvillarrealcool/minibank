package co.com.neoris.minibank.app.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Builder
@Data
public class CuentaDTO {

    private long cuentaId;

    private String estadoCuenta;

    private long numeroDeCuenta;

    private BigDecimal saldoInicial;

    private String tipoCuenta;

    private ClienteDTO cliente;

    public BigDecimal credito;

    public BigDecimal debito;

}
