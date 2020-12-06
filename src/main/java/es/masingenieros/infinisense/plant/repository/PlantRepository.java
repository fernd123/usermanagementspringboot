package es.masingenieros.infinisense.plant.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.plant.Plant;

@RepositoryRestResource(path = "plant")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface PlantRepository extends PagingAndSortingRepository<Plant, String> {
	Optional<Plant> findByName(String name);
	
}
