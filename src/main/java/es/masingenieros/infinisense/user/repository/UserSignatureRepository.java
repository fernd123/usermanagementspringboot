package es.masingenieros.infinisense.user.repository;

import org.springframework.data.repository.CrudRepository;

import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.UserSignature;

public interface UserSignatureRepository extends CrudRepository<UserSignature, String> {

	UserSignature findByUser(User user);

}
