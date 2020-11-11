package es.masingenieros.infinisense.security;

import java.util.Optional;

import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SpringSecurityAuditorAware implements AuditorAware<String> {

	public Optional<String> getCurrentAuditor() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

		if (authentication == null || !authentication.isAuthenticated()) {
			return null;
		}

		if(authentication.getPrincipal().equals("anonymousUser")) {
			return Optional.of("0");
		}else {
			return Optional.of(((CustomUserDetails) authentication.getPrincipal()).getUser().getUuid());
		}
	}
}