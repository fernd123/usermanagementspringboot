package es.masingenieros.infinisense.plant;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonProperty;

import es.masingenieros.infinisense.lib.DomainObject;

@Entity
public class Plant extends DomainObject{

	private static final long serialVersionUID = 1L;

	private String name;

	private String location;

	private String phone;

	private String alternativePhone;
	
	private Integer maximumCapacity;
	
	//@OneToMany(mappedBy = "plant", cascade = CascadeType.ALL)
    //@type="list"(access = JsonProperty.Access.READ_ONLY)
    //private Set<PlantPlane> plantPlane = new HashSet<PlantPlane>();

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getAlternativePhone() {
		return alternativePhone;
	}

	public void setAlternativePhone(String alternativePhone) {
		this.alternativePhone = alternativePhone;
	}

	public Integer getMaximumCapacity() {
		return maximumCapacity;
	}

	public void setMaximumCapacity(Integer maximumCapacity) {
		this.maximumCapacity = maximumCapacity;
	}
	
}
