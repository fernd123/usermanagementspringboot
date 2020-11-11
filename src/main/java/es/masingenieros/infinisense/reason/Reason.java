package es.masingenieros.infinisense.reason;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.visit.Visit;

@Entity
public class Reason extends DomainObject{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;
	
    @OneToMany(mappedBy = "reason", cascade = CascadeType.ALL)
    private Set<Visit> visit = new HashSet<Visit>();


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
