package es.masingenieros.infinisense.plant.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.PlantPlane;

@RepositoryRestResource(path = "plantplane")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})

public interface PlantPlaneRepository extends PagingAndSortingRepository<PlantPlane, String> {

	Optional<PlantPlane> findByPlant(Plant plant);
}
