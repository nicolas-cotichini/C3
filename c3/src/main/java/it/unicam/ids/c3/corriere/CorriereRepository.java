package it.unicam.ids.c3.corriere;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CorriereRepository extends CrudRepository<Corriere, Long> {

	List<Corriere> findByOperativo(boolean operativo);
	
}
