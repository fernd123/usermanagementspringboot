package es.masingenieros.infinisense.sensor.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.sensor.SensorType;
import es.masingenieros.infinisense.sensor.repository.SensorTypeRepository;
import es.masingenieros.infinisense.sensor.service.SensorTypeService;

@Service
public class SensorTypeServiceImpl implements SensorTypeService{

	@Autowired
	SensorTypeRepository sensorTypeRepository;
	
	@Override
	public SensorType save(SensorType sensor) {
		return sensorTypeRepository.save(sensor);
	}

	@Override
	public SensorType update(String uuid, SensorType sensor) {
		Optional<SensorType> optReason = sensorTypeRepository.findById(uuid);
		SensorType sensorInDb = optReason.get();
		sensorInDb.setName(sensor.getName());
		sensorInDb.setDescription(sensor.getDescription());
		sensorInDb.setActive(sensor.getActive());
		return sensorTypeRepository.save(sensorInDb);
	}

	@Override
	public void deleteSensorTypeById(List<String> sensorUuids) {
		for (String id : sensorUuids) {
			sensorTypeRepository.deleteById(id);
		}
	}

	@Override
	public Iterable<SensorType> findAll() {
		return sensorTypeRepository.findAll();
	}

	@Override
	public Optional<SensorType> findSensorTypeByUuid(String uuid) {
		return sensorTypeRepository.findById(uuid);
	}
}
