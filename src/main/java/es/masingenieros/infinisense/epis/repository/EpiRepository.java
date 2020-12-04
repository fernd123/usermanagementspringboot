package es.masingenieros.infinisense.epis.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.epis.Epi;

@RepositoryRestResource(path = "epi")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface EpiRepository extends PagingAndSortingRepository<Epi, String> {
	Optional<Epi> findByName(String name);
}
