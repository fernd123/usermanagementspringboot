package es.masingenieros.infinisense.reason.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.reason.ReasonProjectToken;

@RepositoryRestResource(path = "reasonprojecttoken")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface ReasonProjectTokenRepository extends PagingAndSortingRepository<ReasonProjectToken, String> {
}
