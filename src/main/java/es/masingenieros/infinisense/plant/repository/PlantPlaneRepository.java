package es.masingenieros.infinisense.plant.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.PlantPlane;

@Repository
public interface PlantPlaneRepository extends PagingAndSortingRepository<PlantPlane, String> {

	Optional<PlantPlane> findByPlant(Plant plant);
}
