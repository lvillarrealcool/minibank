package co.com.neoris.minibank.app.repository;

import co.com.neoris.minibank.app.entities.Cliente;
import co.com.neoris.minibank.app.entities.Cuenta;
import co.com.neoris.minibank.app.enums.EstadoCuenta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuentaRepositorio extends JpaRepository<Cuenta, Long> {

    List<Cuenta> findByClienteAndEstadoCuenta(Cliente cliente, EstadoCuenta estado);

    Cuenta findByNumeroDeCuenta(long numeroDeCuenta);

}
