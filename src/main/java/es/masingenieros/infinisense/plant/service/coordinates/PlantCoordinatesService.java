package es.masingenieros.infinisense.plant.service.coordinates;

import java.util.List;
import java.util.Optional;

import es.masingenieros.infinisense.plant.PlantCoordinates;

public interface PlantCoordinatesService{
	PlantCoordinates save(String plantUuid, PlantCoordinates plantCoordinates);
	PlantCoordinates update(String uuid, PlantCoordinates plantCoordinates);
	void deletePlantCoordinate(List<String> coordinatesUuids);
	Iterable<PlantCoordinates> findAll();
	Optional<PlantCoordinates> findById(String uuid);
	Iterable<PlantCoordinates> findByPlantUuid(String plantUuid);
	Optional<PlantCoordinates> findByPlantCoordinatesUuid(String uuid);
}