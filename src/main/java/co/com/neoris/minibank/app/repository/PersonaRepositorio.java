package co.com.neoris.minibank.app.repository;

import co.com.neoris.minibank.app.entities.Persona;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public interface PersonaRepositorio extends CrudRepository<Persona, Long> {
}
