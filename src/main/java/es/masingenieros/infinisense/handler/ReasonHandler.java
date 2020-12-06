package es.masingenieros.infinisense.handler;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import es.masingenieros.infinisense.exceptions.FieldException;
import es.masingenieros.infinisense.reason.Reason;
import es.masingenieros.infinisense.reason.repository.ReasonRepository;

@RepositoryEventHandler(Reason.class) 
@Component
public class ReasonHandler {

	@Autowired
	private ReasonRepository reasonRepository;

	Logger logger = Logger.getLogger("Class reason");

	@HandleBeforeCreate
	public void handleAuthorBeforeCreate(Reason reason) throws FieldException{
		logger.info("Inside Reason Before Create....");
		this.validateSave(reason);
	}

	@HandleBeforeSave
	public void handleAuthorBeforeSave(Reason reason) throws FieldException{
		logger.info("Inside Reason Before Save....");
		this.validateSave(reason);
	}

	private void validateSave(Reason reason) throws FieldException {
		boolean isNew = reason.getUuid() == null;
		Optional<Reason> reasonByName = reasonRepository.findByName(reason.getName());
		boolean exists = reasonByName.isPresent();
		String uuid = exists ? reasonByName.get().getUuid() : null;
		if ( (isNew && exists) || (!isNew && exists && !uuid.equals(reason.getUuid()))) {
			throw new FieldException(ValidationConstants.MSG_VALUE_MUST_BE_UNIQUE, "name", "reason");
		}
	}
}
