package es.masingenieros.infinisense.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import es.masingenieros.infinisense.user.User;

/* Capa para persistir la información */
public interface UserRepository extends PagingAndSortingRepository<User, String> {

	Optional<User> findByUsername(String userName);
	
	@Query("select password from User u where u.id = ?1")
	String getUserPassword(String uuid);

	Optional<User> findByDni(String dni);

	@Query(value = "select u from User u where lower(u.roles) NOT LIKE '%visitor%'") 
	Iterable<User> findByInternalUsers(String tenantId);
}
