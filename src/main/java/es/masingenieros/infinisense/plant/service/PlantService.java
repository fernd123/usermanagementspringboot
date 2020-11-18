package es.masingenieros.infinisense.plant.service;

import java.util.List;
import java.util.Optional;

import es.masingenieros.infinisense.plant.Plant;

public interface PlantService{
	Plant save(Plant plant);
	Plant update(String uuid, Plant plant);
	void deletePlantById(List<String> reasonUuids);
	Iterable<Plant> findAll();
	Optional<Plant> findById(String uuid);
}