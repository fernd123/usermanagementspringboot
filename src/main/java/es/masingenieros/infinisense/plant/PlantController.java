package es.masingenieros.infinisense.plant;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.masingenieros.infinisense.filestorage.FileResponse;
import es.masingenieros.infinisense.filestorage.StorageService;
import es.masingenieros.infinisense.plant.repository.PlantPlaneRepository;
import es.masingenieros.infinisense.plant.service.PlantService;

@RestController
@RequestMapping("/api/plant")
public class PlantController {

	@Autowired 
	PlantService plantService;

	@Autowired
	PlantPlaneRepository plantPlaneRepository;

	@Autowired
	private StorageService storageService;


	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> savePlant(@RequestParam Map<String, String> values) {
		Plant plant = createPlant(values);
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(plantService.save(plant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


	@RequestMapping(value="/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updatePlant(@PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) {
		Plant plant = createPlant(values);
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
	
	@RequestMapping(value="/{uuid}", method=RequestMethod.GET)
	public ResponseEntity<?> getPlant(@PathVariable(value = "uuid") String uuid) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(plantService.findById(uuid));
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
	
	@RequestMapping(value="/{uuid}/planes", method=RequestMethod.GET)
	public ResponseEntity<?> getPlanePlant(@PathVariable(value = "uuid") String uuid) {
		try {
			Optional<Plant> plantOpt = plantService.findById(uuid);
			Plant plant = plantOpt.orElseThrow();
			return ResponseEntity.status(HttpStatus.OK).body(plantPlaneRepository.findByPlant(plant));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> uploadPlane(@PathVariable(value = "uuid") String uuid,
			@RequestParam("file") MultipartFile file) {
		try {
			Optional<Plant> plantOpt = plantService.findById(uuid);
			Plant plant = plantOpt.orElseThrow();


			String name = storageService.store(file);

			String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/download/")
					.path(name)
					.toUriString();

			PlantPlane plantPlane = new PlantPlane();
			plantPlane.setPlant(plant);
			plantPlane.setPath(uri);
			plantPlane.setName(name);
			plantPlaneRepository.save(plantPlane);

			FileResponse fr = new FileResponse(name, uri, file.getContentType(), file.getSize());
			return ResponseEntity.status(HttpStatus.OK).body(fr);
		} catch (Exception e) {

			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/planes/download/{filename}", method=RequestMethod.GET)
	public ResponseEntity<?> downloadFile(@PathVariable(value = "filename") String filename) {
		try {
			Resource resource = storageService.loadAsResource(filename);

			return ResponseEntity.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION,
							"attachment; filename=\"" + resource.getFilename() + "\"")
					.body(resource);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}



	private Plant createPlant(Map<String, String> values) {
		Plant plant = new Plant();
		plant.setName(values.get("name"));
		plant.setLocation(values.get("location"));
		plant.setMaximumCapacity(Integer.parseInt(values.get("maximumCapacity")));
		plant.setPhone(values.get("phone"));
		plant.setAlternativePhone(values.get("alternativePhone"));
		return plant;
	}
}
