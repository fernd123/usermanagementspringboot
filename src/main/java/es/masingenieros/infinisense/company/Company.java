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
	
	private Boolean alira;

	private Boolean infinisense;

	private Boolean active;
	
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

	public Boolean getAlira() {
		return alira;
	}

	public void setAlira(Boolean alira) {
		this.alira = alira;
	}

	public Boolean getInfinisense() {
		return infinisense;
	}

	public void setInfinisense(Boolean infinisense) {
		this.infinisense = infinisense;
	}
}
