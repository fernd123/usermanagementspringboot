package es.masingenieros.infinisense.handler;

import java.util.logging.Logger;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleAfterCreate;
import org.springframework.data.rest.core.annotation.HandleAfterSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import es.masingenieros.infinisense.exceptions.FieldException;
import es.masingenieros.infinisense.plant.PlantCoordinates;
import es.masingenieros.infinisense.plant.repository.PlantCoordinatesRepository;

@RepositoryEventHandler(PlantCoordinates.class) 
@Component
public class PlantCoordinateHandler {

	@Autowired
	private PlantCoordinatesRepository plantCoordinatesRepository;

	Logger logger = Logger.getLogger("Class plant");

	@HandleAfterCreate
	public void handleAuthorBeforeCreate(PlantCoordinates plantCoord) throws FieldException{
		logger.info("Inside Plant After Create....");
		this.assignCoordinateId(plantCoord);
	}

	@HandleAfterSave
	public void handleAuthorBeforeSave(PlantCoordinates plantCoord) throws FieldException{
		logger.info("Inside Plant After Save....");
		this.assignCoordinateId(plantCoord);
	}

	private void assignCoordinateId(PlantCoordinates plantCoords) {
		// Set the idCoord in coordinates attribute
		String coordinates = plantCoords.getCoordinates();

		//convert java object to JSON format
		if(coordinates != null) {
			JSONObject convertedObject = new JSONObject(coordinates);
			convertedObject.put("idCoordenate", plantCoords.getUuid());
			convertedObject.put("type", plantCoords.getVirtualZoneType());
			convertedObject.put("title", plantCoords.getName());

			plantCoords.setCoordinates(convertedObject.toString());
			plantCoords = plantCoordinatesRepository.save(plantCoords);
		}
	}

}
