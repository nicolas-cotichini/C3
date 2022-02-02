package it.unicam.ids.c3.magazziniere;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MagazziniereRepository extends CrudRepository<Magazziniere, Long>{
}
