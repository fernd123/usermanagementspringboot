package es.masingenieros.infinisense.plant.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.PlantCoordinates;

@Repository
public interface PlantCoordinatesRepository extends PagingAndSortingRepository<PlantCoordinates, String> {

	Iterable<PlantCoordinates> findByPlantUuid(String plantUuid);

	Iterable<PlantCoordinates> findByPlantUuidAndVirtualZoneType(String uuid, String type);

	void deleteByPlant(Plant plant);

}
