package es.masingenieros.infinisense.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.user.User;

/* Capa para persistir la información */
@RepositoryRestResource(path = "user")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface UserRepository extends PagingAndSortingRepository<User, String> {

	Optional<User> findByUsername(String userName);
	
	@Query("select password from User u where u.id = ?1")
	String getUserPassword(String uuid);

	@Query(value = "select u from User u where lower(u.roles) = 'visitor' AND lower(u.dni) = :dni") 
	Optional<User> findByDniInternal(@Param("dni") String dni);

	@Query(value = "select u from User u where lower(u.roles) NOT LIKE '%visitor%'") 
	Iterable<User> findByInternalUsers();

	@Query(value = "select u from User u where lower(u.roles) LIKE '%visitor%'") 
	Iterable<User> findByExternalUsers();

	Optional<User> findByDni(String dni);
}
