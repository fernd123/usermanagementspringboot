package es.masingenieros.infinisense.company;

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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import es.masingenieros.infinisense.company.service.CompanyService;
import es.masingenieros.infinisense.filestorage.FileResponse;
import es.masingenieros.infinisense.filestorage.StorageService;
import es.masingenieros.infinisense.mulitenancy.TenantContext;
import es.masingenieros.infinisense.sensor.SensorType;
import es.masingenieros.infinisense.user.User;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

	private static final String COMPANY = "company";
	@Autowired 
	CompanyService companyService;
	
	@Autowired
	private StorageService storageService;
	
	@RequestMapping(value="/create", method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> createCompanySchema(@RequestParam Map<String, String> values) throws Exception{
		Company company = createCompany(values);
		/* Trigger event handler to check data*/
		this.companyService.save(company);
		User userDefault = createUser(values);
		company = companyService.createSchema(company, userDefault);
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(companyService.save(company));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	/*@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> saveCompany(@RequestParam Map<String, String> values) throws Exception{
		Company company = createCompany(values);
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(companyService.save(company));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}*/

	@RequestMapping(value="/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updateCompany(@PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) throws Exception{
		Company company = createCompany(values);
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(companyService.update(uuid, company));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCompany(@PathVariable(value = "uuid") String uuid){
		List<String> companyIds = new ArrayList<String>();
		companyIds.add(uuid);
		try {
			companyService.deleteCompanyByUuid(companyIds);
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(value="/{uuid}", method=RequestMethod.GET)
	public ResponseEntity<?> getCompanyByUuid(@PathVariable(value = "uuid") String uuid) {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.findByUuid(uuid));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
/*
	@GetMapping
	public ResponseEntity<?> getAllCompanys() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}*/
	
	
	/* Image management */
	@RequestMapping(value="/{uuid}/upload", method=RequestMethod.POST, consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<?> updateSensorTypeImage(@PathVariable(value = "uuid") String uuid,
			@RequestParam("file") MultipartFile file) {
		try {

			Optional<Company> sensorOpt = companyService.findByUuid(uuid);
			Company companyInDb = sensorOpt.get();

			// Name must be unique
			String name = storageService.store(file, companyInDb.getName(), companyInDb.getName(), COMPANY);

			String uri = ServletUriComponentsBuilder.fromCurrentContextPath()
					.path("/download/")
					.path(name)
					.toUriString();


			companyInDb.setImage(name);
			companyService.save(companyInDb);

			FileResponse fr = new FileResponse(name, uri, file.getContentType(), file.getSize());
			
			// Update the company schema
			companyService.update(uuid, companyInDb);

			
			return ResponseEntity.status(HttpStatus.OK).body(fr);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(value="/download/{filename}/{tenantName}", method=RequestMethod.GET)
	public ResponseEntity<?> downloadFile(@PathVariable(value = "filename") String filename, @PathVariable(value = "tenantName") String tenantName) {
		try {
			Resource resource = storageService.loadAsResource(filename, tenantName , COMPANY);

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

	private Company createCompany(Map<String, String> values) {
		Company company = new Company();
		company.setName(values.get("name"));
		company.setDescription(values.get("description"));
		company.setEmail(values.get("email"));
		company.setServer(values.get("server"));
		company.setPort(values.get("port"));
		company.setActive(Boolean.valueOf(values.get("active")));
		company.setAliro(Boolean.valueOf(values.get("aliro")));
		company.setErgo(Boolean.valueOf(values.get("ergo")));
		return company;
	}
	
	private User createUser(Map<String, String> values) {
		User user = new User();
		user.setUsername(values.get("username"));
		user.setPassword(values.get("password"));
		user.setFirstname(values.get("firstname"));
		user.setLastName(values.get("lastname"));
		user.setEmail(values.get("email"));
		user.setDni(values.get("dni"));
		user.setRoles("ADMIN");
		user.setActive(true);
		return user;
	}
}
