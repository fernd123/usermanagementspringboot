package es.masingenieros.infinisense.plant.service.coordinates.impl;

import java.util.List;
import java.util.Optional;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.plant.Plant;
import es.masingenieros.infinisense.plant.PlantCoordinates;
import es.masingenieros.infinisense.plant.repository.PlantCoordinatesRepository;
import es.masingenieros.infinisense.plant.repository.PlantRepository;
import es.masingenieros.infinisense.plant.service.coordinates.PlantCoordinatesService;

@Service
public class PlantCoordinatesServiceImp implements PlantCoordinatesService{

	@Autowired
	PlantCoordinatesRepository plantCoordinatesRepository;

	@Autowired
	PlantRepository plantRepository;

	@Override
	public PlantCoordinates save(String plantUuid, PlantCoordinates plantCoordinates) {
		Optional<Plant> plantOpt = plantRepository.findById(plantUuid);
		Plant plant = plantOpt.get();

		plantCoordinates.setPlant(plant);
		PlantCoordinates plantCoords = plantCoordinatesRepository.save(plantCoordinates);

		// Set the idCoord in coordinates attribute
		String coordinates = plantCoords.getCoordinates();

		//convert java object to JSON format
	     JSONObject convertedObject = new JSONObject(coordinates);
		convertedObject.put("idCoordenate", plantCoords.getUuid());
		convertedObject.put("type", plantCoords.getVirtualZoneType());
		convertedObject.put("title", plantCoords.getName());

		plantCoordinates.setCoordinates(convertedObject.toString());
		plantCoords = plantCoordinatesRepository.save(plantCoords);

		return plantCoords;
	}

	@Override
	public PlantCoordinates update(String uuid, PlantCoordinates plantCoordinates) {
		Optional<PlantCoordinates> plantOpt = plantCoordinatesRepository.findById(uuid);
		PlantCoordinates plantInDB = plantOpt.get();
		if(plantCoordinates.getCoordinates() != null && !plantCoordinates.getCoordinates().equals("null")) {
			plantInDB.setCoordinates(plantCoordinates.getCoordinates());			
		}
		plantInDB.setName(plantCoordinates.getName());
		plantInDB.setSensorId(plantCoordinates.getSensorId());
		plantInDB.setSensorType(plantCoordinates.getSensorType());
		plantInDB.setVirtualZoneType(plantCoordinates.getVirtualZoneType());
		plantInDB.setEpis(plantCoordinates.getEpis());
		plantInDB.setStatus(plantCoordinates.getStatus());

		//convert java object to JSON format
	     JSONObject convertedObject = new JSONObject(plantInDB.getCoordinates());
		convertedObject.put("idCoordenate", plantInDB.getUuid());
		convertedObject.put("type", plantInDB.getVirtualZoneType());
		convertedObject.put("title", plantInDB.getName());
		//convertedObject.put("img", plantCoordinates.getSensorType().getImage());

		plantInDB.setCoordinates(convertedObject.toString());
		plantInDB = plantCoordinatesRepository.save(plantInDB);
		
		return plantInDB;
	}

	@Override
	public void deletePlantCoordinate(List<String> coordinatesUuid) {
		for (String id : coordinatesUuid) {
			plantCoordinatesRepository.deleteById(id);
		}
	}

	@Override
	public Iterable<PlantCoordinates> findAll() {
		return plantCoordinatesRepository.findAll();
	}

	@Override
	public Optional<PlantCoordinates> findById(String uuid) {
		return plantCoordinatesRepository.findById(uuid);
	}

	@Override
	public Iterable<PlantCoordinates> findByPlantUuid(String plantUuid) {
		return plantCoordinatesRepository.findByPlantUuid(plantUuid);
	}
	
	@Override
	public Optional<PlantCoordinates> findByPlantCoordinatesUuid(String uuid) {
		return plantCoordinatesRepository.findById(uuid);
	}

	@Override
	public Iterable<PlantCoordinates> findByPlantUuidAndVirtualZoneType(String uuid, String type) {
		return plantCoordinatesRepository.findByPlantUuidAndVirtualZoneType(uuid, type);
	}

	@Override
	public void deletePlantCoordinateByPlant(Plant plant) {
		plantCoordinatesRepository.deleteByPlant(plant);		
	}
}
