package es.masingenieros.infinisense.company;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.h2.command.ddl.CreateUser;
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

import es.masingenieros.infinisense.company.service.CompanyService;
import es.masingenieros.infinisense.user.User;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

	@Autowired 
	CompanyService companyService;
	
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
	

	@GetMapping
	public ResponseEntity<?> getAllCompanys() {
		try {
			return ResponseEntity.status(HttpStatus.OK).body(companyService.findAll());
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	private Company createCompany(Map<String, String> values) {
		Company company = new Company();
		company.setName(values.get("name"));
		company.setDescription(values.get("description"));
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
