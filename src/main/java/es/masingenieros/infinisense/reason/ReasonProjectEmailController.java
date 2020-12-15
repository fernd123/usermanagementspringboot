package es.masingenieros.infinisense.reason;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.masingenieros.infinisense.plant.service.coordinates.PlantCoordinatesService;
import es.masingenieros.infinisense.reason.service.ReasonService;

@RestController
@RequestMapping("/api/project")
public class ReasonProjectEmailController {

	@Autowired 
	ReasonService reasonService;

	@Autowired 
	PlantCoordinatesService plantCoordinatesService;
	
	@RequestMapping(value="{reasonUuid}/project", method=RequestMethod.POST)
	public ResponseEntity<?> createNewProject(@PathVariable(value = "reasonUuid") String reasonUuid,
			@RequestParam(value = "emaillist") List<String> emailList) {
		try {
			reasonService.createProject(emailList, reasonUuid);
			

			return ResponseEntity.status(HttpStatus.OK).body(emailList);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
