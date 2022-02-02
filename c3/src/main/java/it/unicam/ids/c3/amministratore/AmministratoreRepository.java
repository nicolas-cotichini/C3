package it.unicam.ids.c3.amministratore;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AmministratoreRepository extends CrudRepository<Amministratore, Long> {
}
