package it.unicam.ids.c3.interflocker;

import org.springframework.data.repository.CrudRepository;

public interface InterfLockerRepository extends CrudRepository<InterfLocker, Long> {
	
	InterfLocker findByIdLocker(Long idLocker);
}
