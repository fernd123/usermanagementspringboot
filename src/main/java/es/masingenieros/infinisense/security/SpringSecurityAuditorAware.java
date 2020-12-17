package es.masingenieros.infinisense.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		CustomUserDetails customerDetailsInBd = null;
		UserDetails customerWebExternalInMemory = null;
		Optional<String> auditUsername = null;
		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		try {
			customerDetailsInBd = ((CustomUserDetails) authentication.getPrincipal());
		}catch(Exception e) {

		}

		try {
			customerWebExternalInMemory = (UserDetails) authentication.getPrincipal();
		}catch(Exception e) {

		}



		if(authentication.getPrincipal().equals("anonymousUser")) {
			auditUsername = Optional.of("0");
		}else if(customerDetailsInBd != null){
			auditUsername = Optional.of(customerDetailsInBd.getUser().getUuid());
		}else if(customerWebExternalInMemory != null){
			auditUsername = Optional.of(customerWebExternalInMemory.getUsername());
		}else {
			auditUsername = Optional.of("0");
		}
		return auditUsername;
	}
}