package es.masingenieros.infinisense.reason;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.plant.PlantCoordinates;
import es.masingenieros.infinisense.visit.Visit;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
public class Reason extends DomainObject implements Serializable{


	/**
	 * 
	 */
	private static final long serialVersionUID = -8686913333975224466L;

	@Column(nullable = false, unique = true)
	private String name;

	private String description;
	
	private Boolean active;
	
	@Column(nullable = true)
    @OneToMany(mappedBy = "reason", cascade = CascadeType.ALL)
	@JsonIgnore
    //@JsonProperty(access = JsonProperty.Access.AUTO)
    private Set<Visit> visit = new HashSet<Visit>();
 
	@OneToOne
    @JoinColumn(name = "plant_coordinate_id")
	//@JsonIgnore
    private PlantCoordinates plantCoordinate;

	public Set<Visit> getVisit() {
		return visit;
	}

	public void setVisit(Set<Visit> visit) {
		this.visit = visit;
	}

	public PlantCoordinates getPlantCoordinate() {
		return plantCoordinate;
	}

	public void setPlantCoordinate(PlantCoordinates plantCoordinate) {
		this.plantCoordinate = plantCoordinate;
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
