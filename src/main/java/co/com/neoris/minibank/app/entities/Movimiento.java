package co.com.neoris.minibank.app.entities;

import co.com.neoris.minibank.app.enums.TipoMovimiento;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.math.BigDecimal;
import java.sql.Date;

@Builder
@AllArgsConstructor
@Entity(name = "movimientos")
@Data
public class Movimiento {

    public Movimiento() {
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long movimientoId;

    @Column
    private Date fecha;

    @Enumerated(EnumType.STRING)
    private TipoMovimiento tipoMovimiento;

    @Column
    private BigDecimal valor;

    @Column
    private BigDecimal saldo;

    @Column
    private String movimiento;

    @ManyToOne
    private Cuenta cuenta;

}
