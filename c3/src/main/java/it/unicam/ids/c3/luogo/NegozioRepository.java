package it.unicam.ids.c3.luogo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NegozioRepository extends CrudRepository<Negozio, Long>{
	
	Negozio findByIdCommerciante(Long idCommerciante);
	
	Negozio findByNome(String nome);
}
