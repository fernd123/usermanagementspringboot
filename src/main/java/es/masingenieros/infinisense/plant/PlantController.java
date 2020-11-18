package es.masingenieros.infinisense.plant;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.ArrayList;
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
import es.masingenieros.infinisense.plant.repository.PlantCoordinatesRepository;
import es.masingenieros.infinisense.plant.repository.PlantPlaneRepository;
import es.masingenieros.infinisense.plant.service.PlantService;
import es.masingenieros.infinisense.plant.service.coordinates.PlantCoordinatesService;

@RestController
@RequestMapping("/api/plant")
public class PlantController {

	@Autowired 
	PlantService plantService;
	
	@Autowired 
	PlantCoordinatesService plantCoordsService;

	@Autowired
	PlantPlaneRepository plantPlaneRepository;
	
	@Autowired
	PlantCoordinatesRepository plantCoordsRepository;

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
			plantService.deletePlantById(reasonIds);
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
			Plant plant = plantOpt.get();
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
			Plant plant = plantOpt.get();


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
	
	@RequestMapping(value="/{plantUuid}/upload/{uuid}", method=RequestMethod.PUT, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updatePlantPlane(@PathVariable(value = "plantUuid") String plantUuid, @PathVariable(value = "uuid") String uuid,
			@RequestParam("file") MultipartFile file) {
		try {
			Optional<Plant> plantOpt = plantService.findById(plantUuid);
			Plant plant = plantOpt.get();

			String name = storageService.store(file);

			String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/download/")
					.path(name)
					.toUriString();

			Optional<PlantPlane> plantPlaneOpt = this.plantPlaneRepository.findById(uuid);
			PlantPlane plantPlaneInDb = plantPlaneOpt.get();
			
			plantPlaneInDb.setPlant(plant);
			plantPlaneInDb.setPath(uri);
			plantPlaneInDb.setName(name);
			plantPlaneRepository.save(plantPlaneInDb);

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

			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION,
					"attachment; filename=" + resource.getFilename());
			
			FileNameMap fileNameMap = URLConnection.getFileNameMap();
			headers.add(HttpHeaders.CONTENT_TYPE,
					fileNameMap.getContentTypeFor(resource.getFile().getName()));
			
			return ResponseEntity.ok()
					.headers(headers)
					.body(resource);
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	/** PLANT COORDINATES **/
	@RequestMapping(value="/{uuid}/coordinates", method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> savePlantCoordinates(@PathVariable(value = "uuid") String plantUuid, @RequestParam Map<String, String> values) {
		
		PlantCoordinates plantCoordinates = createPlantCoordinates(values);
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(plantCoordsService.save(plantUuid, plantCoordinates));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/{plantUuid}/coordinates/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updatePlantCoordinates(@PathVariable(value = "plantUuid") String plantUuid, @PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) {
		
		PlantCoordinates plantCoordinates = createPlantCoordinates(values);
		
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(plantCoordsService.update(uuid, plantCoordinates));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/{plantUuid}/coordinates/{uuid}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deletePlantCoordinate(@PathVariable(value = "uuid") String uuid){
		try {
			List<String> plantCoordinateUuids = new ArrayList<String>();
			plantCoordinateUuids.add(uuid);
			plantCoordsService.deletePlantCoordinate(plantCoordinateUuids);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/{plantUuid}/coordinates/{uuid}", method=RequestMethod.GET)
	public ResponseEntity<?> getPlantCoordinatesByUuid(@PathVariable(value = "plantUuid") String plantUuid, @PathVariable(value = "uuid") String uuid) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(plantCoordsService.findByPlantCoordinatesUuid(uuid));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	
	@RequestMapping(value="/{uuid}/coordinates", method=RequestMethod.GET)
	public ResponseEntity<?> getPlantCoordinates(@PathVariable(value = "uuid") String uuid) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(plantCoordsService.findByPlantUuid(uuid));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


	private PlantCoordinates createPlantCoordinates(Map<String, String> values) {
		PlantCoordinates plantCoordinates = new PlantCoordinates();
		plantCoordinates.setSensorId(values.get("sensorId"));
		plantCoordinates.setCoordinates(values.get("coordinates"));
		plantCoordinates.setName(values.get("name"));
		plantCoordinates.setVirtualZoneType(values.get("virtualZoneType"));
		plantCoordinates.setSensorType(values.get("sensorType"));
		return plantCoordinates;
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
