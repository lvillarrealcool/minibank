package co.com.neoris.minibank.app.repository;

import co.com.neoris.minibank.app.entities.Cliente;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface ClienteRepositorio extends PersonaBaseRepositorio<Cliente> {
}
