package it.unicam.ids.c3.acquisti;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AcquistoRepository extends CrudRepository<Acquisto, Long> {

	List<Acquisto> findByIdCliente(Long idCliente);

	List<Acquisto> findByIdCommerciante(Long idCommerciante);
	
}
