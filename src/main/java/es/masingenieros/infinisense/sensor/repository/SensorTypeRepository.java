package es.masingenieros.infinisense.sensor.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.sensor.SensorType;

@RepositoryRestResource(path = "sensortype")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface SensorTypeRepository extends CrudRepository<SensorType, String> {
	Optional<SensorType> findByName(String name);
}
