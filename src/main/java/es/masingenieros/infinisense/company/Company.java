package es.masingenieros.infinisense.company;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.user.User;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Company extends DomainObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8369072168789911299L;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;
	
	private String email;

	private String port;
	
	private String server;
	
	private Boolean aliro;

	private Boolean ergo;

	private Boolean active;
	
	private String image; // path
	
	@ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
	private User user;
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Boolean getAliro() {
		return aliro;
	}

	public void setAliro(Boolean aliro) {
		this.aliro = aliro;
	}

	public Boolean getErgo() {
		return ergo;
	}

	public void setErgo(Boolean ergo) {
		this.ergo = ergo;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}
}
