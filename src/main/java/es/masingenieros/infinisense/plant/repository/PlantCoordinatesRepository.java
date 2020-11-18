package es.masingenieros.infinisense.plant.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.plant.PlantCoordinates;

@Repository
public interface PlantCoordinatesRepository extends PagingAndSortingRepository<PlantCoordinates, String> {

	Iterable<PlantCoordinates> findByPlantUuid(String plantUuid);

}
