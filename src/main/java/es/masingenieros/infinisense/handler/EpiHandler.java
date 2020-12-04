package es.masingenieros.infinisense.handler;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import es.masingenieros.infinisense.epis.Epi;
import es.masingenieros.infinisense.epis.repository.EpiRepository;
import es.masingenieros.infinisense.exceptions.FieldException;

@RepositoryEventHandler(Epi.class) 
@Component
public class EpiHandler {

	@Autowired
	private EpiRepository epiRepository;

	Logger logger = Logger.getLogger("Class EpiHandler");

	@HandleBeforeCreate
	public void handleAuthorBeforeCreate(Epi epi) throws FieldException{
		logger.info("Inside Epi Before Create....");
		this.validateSave(epi);
	}

	@HandleBeforeSave
	public void handleAuthorBeforeSave(Epi epi) throws FieldException{
		logger.info("Inside Epi Before Save....");
		this.validateSave(epi);
	}
	

	private void validateSave(Epi epi) throws FieldException {
		boolean isNew = epi.getUuid() == null;
		Optional<Epi> epiByName = epiRepository.findByName(epi.getName());
		boolean epiExists = epiByName.isPresent();
		String uuid = epiExists ? epiByName.get().getUuid() : null;
		if ( (isNew && epiExists) || (epiExists && !uuid.equals(epi.getUuid()))) {
			throw new FieldException(ValidationConstants.MSG_VALUE_MUST_BE_UNIQUE, "name", "epi");
		}
	}
}
