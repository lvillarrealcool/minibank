package co.com.neoris.minibank.app.repository;

import co.com.neoris.minibank.app.entities.Persona;
import org.springframework.data.repository.CrudRepository;

public interface PersonaBaseRepositorio <T extends Persona> extends CrudRepository<T,Long> {

    T findByIdentificacion(String identificacion);

    T deleteByIdentificacion(String identificacion);

}
