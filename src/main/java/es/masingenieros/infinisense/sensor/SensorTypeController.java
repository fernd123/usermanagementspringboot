package es.masingenieros.infinisense.sensor;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.masingenieros.infinisense.filestorage.FileResponse;
import es.masingenieros.infinisense.filestorage.StorageService;
import es.masingenieros.infinisense.mulitenancy.TenantContext;
import es.masingenieros.infinisense.sensor.service.SensorTypeService;

@RestController
@RequestMapping("/api/sensortype")
public class SensorTypeController {
	
	private static final String SENSORTYPE = "sensortype";

	@Autowired 
	SensorTypeService sensorTypeService;

	@Autowired
	private StorageService storageService;
	
	/* Image management */
	@RequestMapping(value="/{uuid}/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateSensorTypeImage(@PathVariable(value = "uuid") String uuid,
			@RequestParam("file") MultipartFile file) {
		try {

			Optional<SensorType> sensorOpt = sensorTypeService.findSensorTypeByUuid(uuid);
			SensorType sensorInDb = sensorOpt.get();

			String name = storageService.store(file, sensorInDb.getUuid(), TenantContext.getCurrentTenant(), SENSORTYPE);

			String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/download/")
					.path(name)
					.toUriString();


			sensorInDb.setImage(name);
			sensorTypeService.save(sensorInDb);

			FileResponse fr = new FileResponse(name, uri, file.getContentType(), file.getSize());
			return ResponseEntity.status(HttpStatus.OK).body(fr);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/download/{filename}", method=RequestMethod.GET)
	public ResponseEntity<?> downloadFile(@PathVariable(value = "filename") String filename) {
		try {
			Resource resource = storageService.loadAsResource(filename, TenantContext.getCurrentTenant(), SENSORTYPE);

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
}
