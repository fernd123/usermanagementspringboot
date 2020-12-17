package es.masingenieros.infinisense.user;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.reason.ReasonProjectParticipant;
import es.masingenieros.infinisense.visit.Visit;

@Entity
public class User extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String username;

	private String password;

	private String firstname;

	private String lastname;
	
	private boolean active;
	
	private String roles;

	private String email;
    
	@Column(nullable = true, unique = true)
    private String dni;
    
	@Column(nullable = true)
    private String company;
		
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<Visit> visit = new HashSet<Visit>();
	
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
    private Set<ReasonProjectParticipant> reasonProjectParticipant = new HashSet<ReasonProjectParticipant>();
	
	/* Recursive problem
	 * @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private UserSignature signature;
    
	public UserSignature getSignature() {
		return signature;
	}

	public void setSignature(UserSignature signature) {
		this.signature = signature;
	}*/

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastName(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public String getCompany() {
		return company;
	}

	public void setCompany(String company) {
		this.company = company;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

}
