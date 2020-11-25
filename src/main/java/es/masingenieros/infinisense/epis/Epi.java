package es.masingenieros.infinisense.epis;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.masingenieros.infinisense.lib.DomainObject;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Epi extends DomainObject implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8369072168789911299L;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;
	
	private Boolean active;
	
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
}
