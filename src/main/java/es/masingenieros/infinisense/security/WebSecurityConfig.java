package es.masingenieros.infinisense.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter{

	@Autowired
	UserDetailsService userDetailsService;

	@Autowired
	JWTAuthorizationFilter jwtRequestFilter;
	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		
		auth.userDetailsService(userDetailsService);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		/*http.csrf().disable()
		.addFilterAfter(new JWTAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class)
		.authorizeRequests()
		.antMatchers("/api/**").authenticated()
		.antMatchers("/", "/home", "/register", "/login","/public/**").permitAll()	
		.anyRequest().authenticated();*/

		http.csrf().disable()
		.cors().and()
		.authorizeRequests()	
		.antMatchers("/api/reason/**").hasAnyAuthority("ADMIN")
		.antMatchers("/api/visit/**").hasAnyAuthority("ADMIN")
		.antMatchers("/api/epis/**").hasAnyAuthority("ADMIN")
		.antMatchers("/api/sensortype/**").hasAnyAuthority("ADMIN")
		.antMatchers("/api/company/**").hasAnyAuthority("MASTER", "ADMIN")
		.antMatchers("/api/public/user/**").hasAnyAuthority("ADMIN", "MASTER")
		.antMatchers("/user").hasAnyRole("ADMIN", "USER")
		.antMatchers("/api/user/authenticate").permitAll()
		.antMatchers("/api/user/**").hasAnyAuthority("ADMIN")
		.antMatchers("/api/**").authenticated()
		.and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
//		.and().formLogin();
		http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
	}
	
	@Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService);
        // Inyecta encoder para decode la contraseña automáticamente 
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}
	
	@Bean
    public SpringSecurityAuditorAware auditorAware() {
        return new SpringSecurityAuditorAware();
    }
	
	

}
