package es.masingenieros.infinisense.user.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

import es.masingenieros.infinisense.user.User;

/* Capa para persistir la información */
public interface UserRepository extends CrudRepository<User, String> {

	Optional<User> findByUserName(String userName);
	
	@Query("select password from User u where u.id = ?1")
	String getUserPassword(String uuid);

}
