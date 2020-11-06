package es.masingenieros.infinisense.plant.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.plant.Plant;

@Repository
public interface PlantRepository extends CrudRepository<Plant, String> {
	Optional<Plant> findByName(String name);
}
