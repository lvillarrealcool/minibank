package co.com.neoris.minibank.app.repository;

import co.com.neoris.minibank.app.entities.Movimiento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;

@Repository
public interface MovimientoRepositorio extends JpaRepository<Movimiento, Long> {

    @Query(value = "SELECT mo.* FROM MOVIMIENTOS mo, CUENTAS cu, CLIENTES cl, PERSONAS pe \n" +
            "WHERE mo.CUENTA_CUENTA_ID=cu.CUENTA_ID AND cu.CLIENTE_PERSONA_ID=cl.PERSONA_ID AND cl.PERSONA_ID=pe.PERSONA_ID\n" +
            "AND mo.FECHA BETWEEN :desde AND :hasta AND pe.IDENTIFICACION=:identificacion", nativeQuery = true)
    List<Movimiento> consultarPorFechaYCliente(@Param("desde")Date desde, @Param("hasta") Date hasta,@Param("identificacion") String identificacion);

}
