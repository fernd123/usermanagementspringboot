package es.masingenieros.infinisense.reason.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.reason.Reason;

@RepositoryRestResource(path = "reason")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface ReasonRepository extends PagingAndSortingRepository<Reason, String> {
	Optional<Reason> findByName(String name);
}
	