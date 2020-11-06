package es.masingenieros.infinisense.plant;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.masingenieros.infinisense.plant.service.PlantService;

@RestController
@RequestMapping("/api/plant")
public class PlantController {
	@Autowired 
	PlantService plantService;

	@PostMapping
	public ResponseEntity<?> savePlant(@RequestBody Plant plant) {
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(plantService.save(plant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@PutMapping("/{uuid}")
	public ResponseEntity<?> updatePlant(@PathVariable(value = "uuid") String uuid, @RequestBody Plant plant) {
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(plantService.update(uuid, plant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@DeleteMapping
	public ResponseEntity<?> deletePlant(@RequestParam List<String> reasonIds){
		try {
			plantService.deleteReasonById(reasonIds);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	@GetMapping
	public ResponseEntity<?> getAllPlants() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(plantService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
