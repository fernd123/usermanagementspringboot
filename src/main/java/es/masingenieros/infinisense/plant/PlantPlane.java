package es.masingenieros.infinisense.plant;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
public class PlantPlane extends DomainObject{

	private static final long serialVersionUID = 1L;
	
	private String path;
	
	private String name;
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plant_id")
	private Plant plant;

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
