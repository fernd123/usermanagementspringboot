package es.masingenieros.infinisense.handler;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.masingenieros.infinisense.exceptions.FieldException;
import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.repository.UserRepository;

@RepositoryEventHandler(User.class) 
@Component
public class UserHandler {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	Logger logger = Logger.getLogger("Class EpiHandler");

	@HandleBeforeCreate
	public void handleAuthorBeforeCreate(User user) throws FieldException{
		logger.info("Inside User Before Create....");
		this.validateSave(user);
	}

	@HandleBeforeSave
	public void handleAuthorBeforeSave(User user) throws FieldException{
		logger.info("Inside User Before Save....");
		this.validateSave(user);
	}


	private void validateSave(User user) throws FieldException {
		boolean isNew = user.getUuid() == null;
		Optional<User> userByDni = userRepository.findByDni(user.getDni());
		boolean userExists = userByDni.isPresent();
		String uuid = userExists ? userByDni.get().getUuid() : null;
		// DNI
		if ( (isNew && userExists) || (userExists && !uuid.equals(user.getUuid()))) {
			throw new FieldException(ValidationConstants.MSG_VALUE_MUST_BE_UNIQUE, "dni", "user");
		}
		
		// Username
		Optional<User> userByUsername = userRepository.findByUsername(user.getUsername());
		userExists = userByUsername.isPresent();
		uuid = userExists ? userByUsername.get().getUuid() : null;
		if ( (isNew && userExists) || (userExists && !uuid.equals(user.getUuid()))) {
			throw new FieldException(ValidationConstants.MSG_VALUE_MUST_BE_UNIQUE, "username", "user");
		}

		if(user.getPassword() != null) {
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			userRepository.save(user);
		}
	}
}

