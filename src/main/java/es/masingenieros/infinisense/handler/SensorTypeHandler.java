package es.masingenieros.infinisense.handler;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterDelete;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import es.masingenieros.infinisense.epis.Epi;
import es.masingenieros.infinisense.exceptions.FieldException;
import es.masingenieros.infinisense.filestorage.StorageService;
import es.masingenieros.infinisense.mulitenancy.TenantContext;
import es.masingenieros.infinisense.sensor.SensorType;
import es.masingenieros.infinisense.sensor.repository.SensorTypeRepository;

@RepositoryEventHandler(SensorType.class) 
@Component
public class SensorTypeHandler {

	@Autowired
	private SensorTypeRepository sensorTypeRepository;
	
	@Autowired
	private StorageService storageService;

	Logger logger = Logger.getLogger("Class SensorTypeHandler");

	@HandleBeforeCreate
	public void handleAuthorBeforeCreate(SensorType sensorType) throws FieldException{
		logger.info("Inside Sensor Type Before Create....");
		this.validateSave(sensorType);
	}

	@HandleBeforeSave
	public void handleAuthorBeforeSave(SensorType sensorType) throws FieldException{
		logger.info("Inside Sensor Type Before Save....");
		this.validateSave(sensorType);
	}
	
	@HandleAfterDelete
	public void handleAuthorAfterDelete(SensorType sensorType) throws FieldException{
		logger.info("Inside Sensor Type After Delete....");
		this.storageService.deleteResource(sensorType.getImage(), TenantContext.getCurrentTenant(), "sensortype");
	}

	private void validateSave(SensorType sensorType) throws FieldException {
		boolean isNew = sensorType.getUuid() == null;
		Optional<SensorType> sensorTypeByName = sensorTypeRepository.findByName(sensorType.getName());
		boolean exists = sensorTypeByName.isPresent();
		String uuid = exists ? sensorTypeByName.get().getUuid() : null;
		if ( (isNew && exists) || (!isNew && exists && !uuid.equals(sensorType.getUuid()))) {
			throw new FieldException(ValidationConstants.MSG_VALUE_MUST_BE_UNIQUE, "name", "sensortype");
		}
	}
}
