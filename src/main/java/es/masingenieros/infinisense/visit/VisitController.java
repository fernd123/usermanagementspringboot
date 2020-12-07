package es.masingenieros.infinisense.visit;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.masingenieros.infinisense.filestorage.StorageService;
import es.masingenieros.infinisense.mulitenancy.TenantContext;
import es.masingenieros.infinisense.reason.service.ReasonService;
import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.UserSignature;
import es.masingenieros.infinisense.user.service.UserService;
import es.masingenieros.infinisense.visit.service.VisitService;

@RestController
@RequestMapping("/api/visit")
public class VisitController {

	@Autowired 
	VisitService visitService;

	@Autowired
	UserService userService;

	@Autowired 
	ReasonService reasonService;
	
	@Autowired
	EntityManager em;

	@Autowired
	private StorageService storageService;
	
	private final String USERSIGNATURE = "usersignature";
	
	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> createExternalUser(@RequestParam Map<String, String> values) throws Exception{
		try {
			JSONObject userJsonObj = new JSONObject(values.get("user"));
			/*JSONObject reasonJsonObj = new JSONObject(values.get("visit"));*/
			/*String reasonUuid = (String) reasonJsonObj.get("reason");*/
			String dni = (String) userJsonObj.get("dni");

			/* Get user */
			Optional<User> userOpt = userService.getUserByDni(dni);
			User user = userOpt.isPresent() ? userOpt.get() : new User();
			if(!userOpt.isPresent()) {
				user.setDni(dni);
				user.setCompany(userJsonObj.getString("company"));
				user.setFirstname(userJsonObj.getString("firstname"));
				user.setLastName(userJsonObj.getString("lastname"));
				user.setRoles(userJsonObj.getString("roles"));
				user.setEmail(userJsonObj.getString("email")); 
				user.setActive(true);
				user = userService.save(user);
				
				UserSignature usignature = new UserSignature();
				String path = convertBaseIntoImage(userJsonObj.getString(("signature")), user.getDni());
				usignature.setPath(path);
				usignature.setUser(user);
				userService.saveSignature(usignature);
			}

			Date now = new Date();
			Visit visit = new Visit();
			visit.setStartDate(new Timestamp(now.getTime()));
			visit.setUser(user);			

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(visitService.save(visit));
			
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	private String convertBaseIntoImage(String base64String, String userUuid) {
        String[] strings = base64String.split(",");
        String extension;
        switch (strings[0]) {//check image's extension
            case "data:image/jpeg;base64":
                extension = "jpeg";
                break;
            case "data:image/png;base64":
                extension = "png";
                break;
            default://should write cases for more images types
                extension = "jpg";
                break;
        }
        //convert base64 string to binary data
        byte[] data = DatatypeConverter.parseBase64Binary(strings[1]);
        String tenant = TenantContext.getCurrentTenant();
        Path rootLocation = Paths.get(this.storageService.getPathOriginal()+"/"+tenant+"/"+USERSIGNATURE);
    	
    	File directory = new File(rootLocation.toString());
        if (!directory.exists()){
            directory.mkdirs();
        }
        String path = rootLocation.toString() +"/"+ userUuid + "." + extension;
        File file = new File(path);
        
        try (OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(file))) {
            outputStream.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return userUuid + "." + extension;
	}
	
	@RequestMapping(method=RequestMethod.PUT, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> updateVisit(@RequestParam Map<String, String> values) throws Exception{
		String uuid = values.get("uuid");
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(visitService.update(uuid));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(method=RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> getAllVisits(@RequestParam Map<String, String> values) {
		String filter = values.get("filter");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(visitService.findAll(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
