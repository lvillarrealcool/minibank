package co.com.neoris.minibank.app.dtos;

import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Builder
@Data
public class MovimientoDTO {

    private LocalDate fecha;
    private String cliente;
    private String numeroCuenta;
    private String tipo;
    private BigDecimal saldoIncial;
    private Boolean estado;
    private BigDecimal movimiento;
    private BigDecimal saldoDisponible;
}
