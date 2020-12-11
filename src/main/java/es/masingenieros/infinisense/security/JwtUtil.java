package es.masingenieros.infinisense.security;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.company.Company;
import es.masingenieros.infinisense.company.repository.CompanyRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {

	@Autowired
	CompanyRepository companyRepository;
	
	private String SECRET_KEY = "Of'ieOf7K6)x,Qr";

	public String extractUsername(String token) {
		return extractClaim(token, Claims::getSubject);
	}

	public Date extractExpiration(String token) {
		return extractClaim(token, Claims::getExpiration);
	}

	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}

	private Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
	}
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	public String generateToken(CustomUserDetails userDetails) throws Exception {
		Map<String, Object> claims = new HashMap<>();
		return createToken(claims, userDetails);
	}
	private String createToken(Map<String, Object> claims, CustomUserDetails userDetails) throws Exception {
		Iterable<Company> companyOpt = this.companyRepository.findAll();
		Company company = companyOpt.iterator().hasNext() ? companyOpt.iterator().next() : null;
		if(company == null) {
			throw new Exception("Must be exist a company!");
		}
		return Jwts.builder().setClaims(claims)
				.setSubject(userDetails.getUsername()).setIssuedAt(new Date(System.currentTimeMillis()))
				.claim("authorities", userDetails.getAuthorities())// Custom data
				.claim("uuid", userDetails.getUuid())// Custom data
				.claim("ergo", company.getErgo())// Custom data
				.claim("aliro", company.getAliro())// Custom data
				.setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
				.signWith(SignatureAlgorithm.HS384, SECRET_KEY).compact();
	}
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.contentEquals(userDetails.getUsername()) && !isTokenExpired(token));
	}


}
