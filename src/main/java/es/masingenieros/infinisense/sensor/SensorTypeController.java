package es.masingenieros.infinisense.sensor;

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

import es.masingenieros.infinisense.sensor.service.SensorTypeService;

@RestController
@RequestMapping("/api/sensortype")
public class SensorTypeController {
	
	@Autowired 
	SensorTypeService sensorTypeService;

	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> saveReason(@RequestParam Map<String, String> values) throws Exception{
		SensorType sensorType = createSensorType(values);
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(sensorTypeService.save(sensorType));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updateReason(@PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) throws Exception{
		SensorType sensorType = createSensorType(values);
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(sensorTypeService.update(uuid, sensorType));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/{uuid}", method=RequestMethod.GET)
	public ResponseEntity<?> updateReason(@PathVariable(value = "uuid") String uuid) throws Exception{
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(sensorTypeService.findSensorTypeByUuid(uuid));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/{uuid}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteSensorType(@PathVariable(value = "uuid") String uuid){
		List<String> sensorTypeIds = new ArrayList<String>();
		sensorTypeIds.add(uuid);
		try {
			sensorTypeService.deleteSensorTypeById(sensorTypeIds);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	
	@GetMapping
	public ResponseEntity<?> getAllReasons() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(sensorTypeService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	private SensorType createSensorType(Map<String, String> values) {
		SensorType sensorType = new SensorType();
		sensorType.setName(values.get("name"));
		sensorType.setDescription(values.get("description"));
		sensorType.setActive(Boolean.valueOf(values.get("active")));
		return sensorType;
	}
}
