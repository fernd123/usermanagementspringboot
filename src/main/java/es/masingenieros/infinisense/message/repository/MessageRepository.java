package es.masingenieros.infinisense.message.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.message.Message;
import es.masingenieros.infinisense.message.MessageEnum;

@RepositoryRestResource(path = "message")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface MessageRepository extends PagingAndSortingRepository<Message, String> {

	
	Message findByType(@Param ("type") MessageEnum type);

}
