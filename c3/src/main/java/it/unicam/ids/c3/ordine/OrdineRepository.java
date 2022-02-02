package it.unicam.ids.c3.ordine;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrdineRepository extends CrudRepository<Ordine, Long>{

	List<Ordine> findByIdCliente(Long idCliente);
	
}
