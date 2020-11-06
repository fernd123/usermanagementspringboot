package es.masingenieros.infinisense.plant.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.repository.PlantRepository;
import es.masingenieros.infinisense.plant.service.PlantService;
import es.masingenieros.infinisense.reason.service.repository.ReasonRepository;

@Service
public class PlantServiceImpl implements PlantService{

	@Autowired
	PlantRepository plantRepository;
	
	@Override
	public Plant save(Plant plant) {
		return plantRepository.save(plant);
	}

	@Override
	public Plant update(String id, Plant plant) {
		Optional<Plant> optReason = plantRepository.findById(id);
		Plant plantInDB = optReason.get();
		plantInDB.setName(plant.getName());
//		plantInDB.setDescription(plant.getDescription());
		return plantRepository.save(plantInDB);

	}

	@Override
	public void deleteReasonById(List<String> userUuids) {
		for (String id : userUuids) {
			plantRepository.deleteById(id);
		}
	}

	@Override
	public Iterable<Plant> findAll() {
		return plantRepository.findAll();
	}
}
