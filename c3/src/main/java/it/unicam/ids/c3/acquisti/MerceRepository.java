package it.unicam.ids.c3.acquisti;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MerceRepository extends CrudRepository<Merce, Long> {

	List<Merce> findByListaNegozi(Long idNegozio);

	Merce findByNome(String nome);
	
}
