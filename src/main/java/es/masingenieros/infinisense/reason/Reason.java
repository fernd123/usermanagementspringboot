package es.masingenieros.infinisense.reason;

import javax.persistence.Column;
import javax.persistence.Entity;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
public class Reason extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;

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
	
	
	
	
}
