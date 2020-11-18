package es.masingenieros.infinisense.sensor.service;

import java.util.List;
import java.util.Optional;

import es.masingenieros.infinisense.sensor.SensorType;


public interface SensorTypeService {

	SensorType save(SensorType sensorType);
	SensorType update(String uuid, SensorType sensorType);
	void deleteSensorTypeById(List<String> sensorUuids);
	Iterable<SensorType> findAll();
	Optional<SensorType> findSensorTypeByUuid(String uuid);
}
