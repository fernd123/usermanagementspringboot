package es.masingenieros.infinisense.reason;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> saveReason(@RequestParam Map<String, String> values) throws Exception{
		Reason reason = createReason(values);
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(reasonService.save(reason));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updateReason(@PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) throws Exception{
		Reason reason = createReason(values);
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(reasonService.update(uuid, reason));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteReason(@PathVariable(value = "uuid") String uuid){
		List<String> reasonsIds = new ArrayList<String>();
		reasonsIds.add(uuid);
		try {
			reasonService.deleteReasonById(reasonsIds);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.GET)
	public ResponseEntity<?> getReasonByUuid(@PathVariable(value = "uuid") String uuid) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reasonService.findReasonByUuid(uuid));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@GetMapping
	public ResponseEntity<?> getAllReasons() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(reasonService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	private Reason createReason(Map<String, String> values) {
		Reason reason = new Reason();
		reason.setName(values.get("name"));
		reason.setDescription(values.get("description"));
		reason.setActive(Boolean.valueOf(values.get("active")));

		String plantZoneId = values.get("plantZone");
		PlantCoordinates plantCoords = null;
		if(plantZoneId != null) {			
			Optional<PlantCoordinates> planCoordinatesOpt = this.plantCoordinatesService.findById(values.get("plantZone"));
			plantCoords = planCoordinatesOpt.isPresent() ? planCoordinatesOpt.get() : null;
			if(plantCoords != null)
				reason.setPlantZone(plantCoords);
		}

		return reason;
	}
}
