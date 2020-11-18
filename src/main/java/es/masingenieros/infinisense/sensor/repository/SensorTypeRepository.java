package es.masingenieros.infinisense.sensor.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.sensor.SensorType;

@Repository
public interface SensorTypeRepository extends CrudRepository<SensorType, String> {
	Optional<SensorType> findByName(String name);
}
