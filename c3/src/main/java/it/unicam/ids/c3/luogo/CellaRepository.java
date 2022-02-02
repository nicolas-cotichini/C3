package it.unicam.ids.c3.luogo;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CellaRepository extends CrudRepository<Cella, Long>{
	
	List<Cella> findAllByIdOrdine(Long idOrdine);
	
	List<Cella> findAllByIdLocker(Long idLocker);

}
