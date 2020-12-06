package es.masingenieros.infinisense.reason;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import es.masingenieros.infinisense.plant.PlantCoordinates;
import es.masingenieros.infinisense.plant.service.coordinates.PlantCoordinatesService;
import es.masingenieros.infinisense.reason.service.ReasonService;

@RestController
@RequestMapping("/api/reason")
public class ReasonController {

	@Autowired 
	ReasonService reasonService;

	@Autowired 
	PlantCoordinatesService plantCoordinatesService;
	
	@RequestMapping(value="/{uuid}/zone", method=RequestMethod.GET)
	public ResponseEntity<?> getZoneReasonByUuid(@PathVariable(value = "uuid") String uuid) {
		Optional<Reason> opt = reasonService.findReasonByUuid(uuid);
		PlantCoordinates plantcoords = opt.isPresent() ? opt.get().getPlantCoordinate() : null;
		
		try {
			return ResponseEntity.status(HttpStatus.OK).body(plantcoords);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	
}
