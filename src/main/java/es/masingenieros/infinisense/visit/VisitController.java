package es.masingenieros.infinisense.visit;

import java.io.ByteArrayInputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.Base64;

import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import es.masingenieros.infinisense.reason.Reason;
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

	@RequestMapping(method=RequestMethod.POST, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> createVisit(@RequestParam Map<String, String> values) throws Exception{
		try {
			JSONObject userJsonObj = new JSONObject(values.get("user"));
			JSONObject reasonJsonObj = new JSONObject(values.get("visit"));
			String reasonUuid = (String) reasonJsonObj.get("reason");
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
				
				/* Signature user */
				String signatureStr = userJsonObj.getString("signature");
	            Blob b = new javax.sql.rowset.serial.SerialBlob(signatureStr.getBytes());
				UserSignature usignature = new UserSignature();
				usignature.setSignature(b);
				usignature.setUser(user);
				userService.saveSignature(usignature);
			}

			/* Get reason */
			Optional<Reason> reason = reasonService.getReasonByUuid(reasonUuid);
			if(reason.get() == null) {
				throw new Exception("Reason with uuid "+ reasonUuid + "does not exist");
			}

			Date now = new Date();
			Visit visit = new Visit();
			visit.setStartDate(new Timestamp(now.getTime()));
			visit.setUser(user);
			visit.setReason(reason.get());

			return ResponseEntity.status(HttpStatus.CREATED)
					.body(visitService.save(visit));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
	
	@RequestMapping(method=RequestMethod.PUT)
	@Transactional(rollbackOn = Exception.class)
	public ResponseEntity<?> updateVisit(@RequestBody Visit visit) throws Exception{
		try {
			return ResponseEntity.status(HttpStatus.CREATED)
					.body(visitService.update(visit));
		}catch(Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}

	@RequestMapping(method=RequestMethod.GET, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ResponseEntity<?> getAllVisits(@RequestParam Map<String, String> values) {
		String tenantId = values.get("tenantId");
		String filter = values.get("filter");
		try {
			return ResponseEntity.status(HttpStatus.OK).body(visitService.findAll(filter));
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
		}
	}
}
