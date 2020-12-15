package es.masingenieros.infinisense.reason.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.reason.Reason;
import es.masingenieros.infinisense.reason.ReasonProjectEmail;

@RepositoryRestResource(path = "reasonprojectemail")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface ReasonProjectEmailRepository extends PagingAndSortingRepository<ReasonProjectEmail, String> {

	 Optional<ReasonProjectEmail> findByReasonAndEmail(Reason reason, String email);
}
	