package it.unicam.ids.c3.commerciante;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommercianteRepository extends CrudRepository<Commerciante, Long> {

	Commerciante findByIdNegozio(Long idNegozio);	
	
}
