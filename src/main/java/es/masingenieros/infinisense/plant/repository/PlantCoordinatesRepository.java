package es.masingenieros.infinisense.plant.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.PlantCoordinates;

@RepositoryRestResource(path = "plantCoordinate")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface PlantCoordinatesRepository extends PagingAndSortingRepository<PlantCoordinates, String> {

	Iterable<PlantCoordinates> findByPlantUuid(String plantUuid);

	Iterable<PlantCoordinates> findByPlantUuidAndVirtualZoneType(String uuid, String type);

	void deleteByPlant(Plant plant);
	
	@RestResource(path = "type", rel = "getByType")
    List<PlantCoordinates> findByVirtualZoneType(@Param("type") String type);//;();

}
