package es.masingenieros.infinisense.reason;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.PlantCoordinates;
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
	
	private Boolean active;
	
	@Column(nullable = true)
    @OneToMany(mappedBy = "reason", cascade = CascadeType.ALL)
    //@JsonProperty(access = JsonProperty.Access.AUTO)
    private Set<Visit> visit = new HashSet<Visit>();

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plant_coordinate_id")
    private PlantCoordinates plantZone;

	public Set<Visit> getVisit() {
		return visit;
	}

	public void setVisit(Set<Visit> visit) {
		this.visit = visit;
	}

	public PlantCoordinates getPlantZone() {
		return plantZone;
	}

	public void setPlantZone(PlantCoordinates plantZone) {
		this.plantZone = plantZone;
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
	
	
	
	
}
