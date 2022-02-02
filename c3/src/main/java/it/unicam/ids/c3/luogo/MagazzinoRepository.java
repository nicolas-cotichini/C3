package it.unicam.ids.c3.luogo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazzinoRepository extends CrudRepository<Magazzino, Long>{
}
