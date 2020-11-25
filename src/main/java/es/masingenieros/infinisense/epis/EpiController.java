package es.masingenieros.infinisense.epis;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

import es.masingenieros.infinisense.epis.service.EpiService;

@RestController
@RequestMapping("/api/epi")
public class EpiController {

	@Autowired 
	EpiService epiService;

	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> saveEpi(@RequestParam Map<String, String> values) throws Exception{
		Epi epi = createEpi(values);
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(epiService.save(epi));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updateEpi(@PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) throws Exception{
		Epi epi = createEpi(values);
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(epiService.update(uuid, epi));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteEpi(@PathVariable(value = "uuid") String uuid){
		List<String> epiIds = new ArrayList<String>();
		epiIds.add(uuid);
		try {
			epiService.deleteEpisByUuid(epiIds);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.GET)
	public ResponseEntity<?> getEpiByUuid(@PathVariable(value = "uuid") String uuid) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(epiService.findByUuid(uuid));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	

	@GetMapping
	public ResponseEntity<?> getAllEpis() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(epiService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	private Epi createEpi(Map<String, String> values) {
		Epi epi = new Epi();
		epi.setName(values.get("name"));
		epi.setDescription(values.get("description"));
		epi.setActive(Boolean.valueOf(values.get("active")));
		return epi;
	}
}
