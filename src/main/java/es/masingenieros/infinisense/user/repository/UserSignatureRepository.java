package es.masingenieros.infinisense.user.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.UserSignature;

public interface UserSignatureRepository extends PagingAndSortingRepository<UserSignature, String> {

	UserSignature findByUser(User user);
	
	

}
