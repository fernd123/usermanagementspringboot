package es.masingenieros.infinisense.plant.service;

import java.util.List;

import es.masingenieros.infinisense.plant.Plant;

public interface PlantService{
	Plant save(Plant plant);
	Plant update(String id, Plant plant);
	void deleteReasonById(List<String> reasonUuids);
	Iterable<Plant> findAll();
}