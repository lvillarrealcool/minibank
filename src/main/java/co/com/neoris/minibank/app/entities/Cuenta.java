package co.com.neoris.minibank.app.entities;

import co.com.neoris.minibank.app.enums.EstadoCuenta;
import co.com.neoris.minibank.app.enums.TipoDeCuenta;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Builder
@AllArgsConstructor
@Entity(name = "cuentas")
@Data
public class Cuenta {

    public Cuenta() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cuentaId;

    @Column(unique = true)
    private long numeroDeCuenta;

    @Enumerated(EnumType.STRING)
    private TipoDeCuenta tipoDeCuenta;

    @Column
    private BigDecimal saldoInicial;

    @Enumerated(EnumType.STRING)
    private EstadoCuenta estadoCuenta;

    @Column
    private BigDecimal saldoDisponible;

    @OneToOne
    private Cliente cliente;

}
