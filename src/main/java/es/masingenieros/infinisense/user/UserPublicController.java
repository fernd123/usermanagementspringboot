package es.masingenieros.infinisense.user;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import es.masingenieros.infinisense.user.service.UserService;

@RestController
@RequestMapping("/api/public/user")
public class UserPublicController {
	@Autowired
	private UserService userService;

	@RequestMapping("/{dni}")
	public ResponseEntity<?> getUserByDni(@PathVariable(value = "dni") String dni) throws Exception{
		try {
			return ResponseEntity.ok(userService.getUserByDni(dni));
		}catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getLocalizedMessage());
		}
	}
	
	@RequestMapping(value="/{uuid}/signature", method=RequestMethod.GET, produces = MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody ResponseEntity<?> getUserSignature(@PathVariable(value = "uuid") String uuid) throws IOException, SQLException {
		Optional<User> user = this.userService.getUserByUuid(uuid);
		UserSignature signature = this.userService.getSignatureByUser(user.get());
		String str = new String(signature.getSignature().getBytes(1l, (int) signature.getSignature().length()));
//		str = str.substring(22);

		return ResponseEntity.status(HttpStatus.CREATED)
				.body(str);
		//data:image/png;base64,
	}
}
