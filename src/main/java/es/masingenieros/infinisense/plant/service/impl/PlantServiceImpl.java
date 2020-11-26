package es.masingenieros.infinisense.plant.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.repository.PlantRepository;
import es.masingenieros.infinisense.plant.service.PlantService;
import es.masingenieros.infinisense.reason.repository.ReasonRepository;

@Service
public class PlantServiceImpl implements PlantService{

	@Autowired
	PlantRepository plantRepository;
	
	@Override
	public Plant save(Plant plant) {
		return plantRepository.save(plant);
	}

	@Override
	public Plant update(String uuid, Plant plant) {
		Optional<Plant> optReason = plantRepository.findById(uuid);
		Plant plantInDB = optReason.get();
		plantInDB.setName(plant.getName());
		plantInDB.setPhone(plant.getPhone());
		plantInDB.setLocation(plant.getLocation());
		plantInDB.setAlternativePhone(plant.getAlternativePhone());
		plantInDB.setMaximumCapacity(plant.getMaximumCapacity());
		return plantRepository.save(plantInDB);

	}

	@Override
	public void deletePlantById(List<String> userUuids) {
		for (String id : userUuids) {
			plantRepository.deleteById(id);
		}
	}

	@Override
	public Iterable<Plant> findAll() {
		return plantRepository.findAll();
	}

	@Override
	public Optional<Plant> findById(String uuid) {
		return plantRepository.findById(uuid);
	}
}
