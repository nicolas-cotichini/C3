package it.unicam.ids.c3.luogo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LockerRepository extends CrudRepository<Locker, Long> {
	
	Locker findByNome(String nome);
}
