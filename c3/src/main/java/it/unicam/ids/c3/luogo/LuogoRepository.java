package it.unicam.ids.c3.luogo;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface LuogoRepository <T extends Luogo> extends CrudRepository<Luogo, Long> {

	Luogo findByIndirizzo(String indirizzo);
	
	List<Luogo> findAllByTipo(String tipo);
	
	Boolean existsByNome(String nome);
	
}
