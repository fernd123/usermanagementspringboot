package es.masingenieros.infinisense.handler;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import es.masingenieros.infinisense.exceptions.FieldException;
import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.repository.PlantRepository;

@RepositoryEventHandler(Plant.class) 
@Component
public class PlantHandler {

	@Autowired
	private PlantRepository plantRepository;

	Logger logger = Logger.getLogger("Class plant");

	@HandleBeforeCreate
	public void handleAuthorBeforeCreate(Plant plant) throws FieldException{
		logger.info("Inside Plant Before Create....");
		this.validateSave(plant);
	}

	@HandleBeforeSave
	public void handleAuthorBeforeSave(Plant plant) throws FieldException{
		logger.info("Inside Plant Before Save....");
		this.validateSave(plant);
	}

	private void validateSave(Plant plant) throws FieldException {
		boolean isNew = plant.getUuid() == null;
		Optional<Plant> plantByName = plantRepository.findByName(plant.getName());
		boolean exists = plantByName.isPresent();
		String uuid = exists ? plantByName.get().getUuid() : null;
		if ( (isNew && exists) || (!isNew && exists && !uuid.equals(plant.getUuid()))) {
			throw new FieldException(ValidationConstants.MSG_VALUE_MUST_BE_UNIQUE, "name", "plant");
		}
	}
}
