package es.masingenieros.infinisense.plant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import es.masingenieros.infinisense.lib.DomainObject;
import es.masingenieros.infinisense.sensor.SensorType;

@Entity
@Table(name = "plant_coordinates")
public class PlantCoordinates extends DomainObject{

	private static final long serialVersionUID = 1L;
	
	private String name;

	@Lob
	private String coordinates;
	
	@Column(name="virtual_zone_type")
	private String virtualZoneType; // Sensor, zona virtual
	
	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "sensor_type_id")
	private SensorType sensorType;
	
	@Column(name="sensor_id")
	private String sensorId;
	
	private String epis;
	
	private String status;

	@OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "plant_id")
	private Plant plant;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCoordinates() {
		return coordinates;
	}

	public void setCoordinates(String coordinate) {
		this.coordinates = coordinate;
	}

	public String getVirtualZoneType() {
		return virtualZoneType;
	}

	public void setVirtualZoneType(String virtualZoneType) {
		this.virtualZoneType = virtualZoneType;
	}

	public SensorType getSensorType() {
		return sensorType;
	}

	public void setSensorType(SensorType sensorType) {
		this.sensorType = sensorType;
	}

	public String getSensorId() {
		return sensorId;
	}

	public void setSensorId(String sensorId) {
		this.sensorId = sensorId;
	}

	public Plant getPlant() {
		return plant;
	}

	public void setPlant(Plant plant) {
		this.plant = plant;
	}

	public String getEpis() {
		return epis;
	}

	public void setEpis(String epis) {
		this.epis = epis;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}
