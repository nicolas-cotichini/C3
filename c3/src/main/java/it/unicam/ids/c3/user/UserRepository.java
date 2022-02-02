package it.unicam.ids.c3.user;

import java.util.List;

import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public interface UserRepository<T extends User> extends CrudRepository<User, Long> {

	Boolean existsByEmail(String email);

	User findByEmail(String email);

	List<User> findAllByRuolo(String ruolo);

}
