package es.masingenieros.infinisense.user;

import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.masingenieros.infinisense.filestorage.StorageService;
import es.masingenieros.infinisense.mulitenancy.TenantContext;
import es.masingenieros.infinisense.user.service.UserService;

@RestController
@RequestMapping("/api/public/user")
public class UserPublicController {
	@Autowired
	private UserService userService;

	@Autowired
	private StorageService storageService;
	
	@RequestMapping(value="/profile/{uuid}", method=RequestMethod.GET)
	public ResponseEntity<?> getUserByUuid(@PathVariable(value = "uuid") String uuid) throws Exception{
		try {
			return ResponseEntity.ok(userService.getUserByUuid(uuid));
		}catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="/{dni}", method=RequestMethod.GET)
	public ResponseEntity<?> getUserByDni(@PathVariable(value = "dni") String dni) throws Exception{
		try {
			return ResponseEntity.ok(userService.getUserByDni(dni));
		}catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}

	@RequestMapping(method=RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> getInternalUsers(@RequestParam Map<String, String> values) {
		return ResponseEntity.ok(userService.getInternalUsers());
	}
	
	@RequestMapping(value="/external", method=RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> getExternalUsers(@RequestParam Map<String, String> values) {
		return ResponseEntity.ok(userService.getExternalUsers());
	}
	
	@RequestMapping(value="/{uuid}/signature", method=RequestMethod.GET)
	public @ResponseBody  ResponseEntity<?> getUserSignature(@PathVariable(value = "uuid") String uuid) {
		try {
			
			Optional<User> user = userService.getUserByUuid(uuid);
			UserSignature signature = userService.getSignatureByUser(user.get());
			
			Resource resource = storageService.loadAsResource(signature.getPath(),TenantContext.getCurrentTenant(), "usersignature");

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
	
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> saveUser(@RequestParam Map<String, String> values) {
		User user = createUser(values);
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(userService.save(user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}


	@RequestMapping(value="/{uuid}", method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> updateUser(@PathVariable(value = "uuid") String uuid, @RequestParam Map<String, String> values) {
		User user = createUser(values);
		try {
			return ResponseEntity.status(HttpStatus.OK)
					.body(userService.update(uuid, user));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	private User createUser(Map<String, String> values) {
		User user = new User();
		user.setUsername(values.get("username"));
		user.setFirstname(values.get("firstname"));
		user.setLastName(values.get("lastname"));
		user.setPassword(values.get("password"));
		user.setRoles(values.get("roles"));
		user.setEmail(values.get("email"));
		user.setDni(values.get("dni"));
		user.setActive(Boolean.valueOf(values.get("active")));
		return user;
	}
}
