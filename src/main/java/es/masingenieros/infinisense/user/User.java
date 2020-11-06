package es.masingenieros.infinisense.user;

import javax.persistence.Column;
import javax.persistence.Entity;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
public class User extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userName;

	private String password;

	private String firstName;

	private String lastName;
	
	private boolean active;
	
	private String roles;

	@Column(nullable = false, unique = true)
	private String email;
    
	@Column(nullable = false, unique = true)
    private String dni;
    
	@Column(nullable = false)
    private String company;
    
	private String token;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
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
