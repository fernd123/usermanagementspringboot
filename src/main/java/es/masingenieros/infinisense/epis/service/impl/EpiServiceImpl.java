package es.masingenieros.infinisense.epis.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.epis.Epi;
import es.masingenieros.infinisense.epis.repository.EpiRepository;
import es.masingenieros.infinisense.epis.service.EpiService;

@Service
public class EpiServiceImpl implements EpiService{

	@Autowired
	EpiRepository epiRepository;
	
	@Override
	public Epi save(Epi epi) {
		return epiRepository.save(epi);
	}

	@Override
	public Epi update(String uuid, Epi epi) {
		Optional<Epi> optReason = epiRepository.findById(uuid);
		Epi epiInDb = optReason.get();
		epiInDb.setName(epi.getName());
		epiInDb.setDescription(epi.getDescription());
		epiInDb.setActive(epi.getActive());
		return epiRepository.save(epiInDb);
	}

	@Override
	public void deleteEpisByUuid(List<String> episUuid) {
		for (String id : episUuid) {
			epiRepository.deleteById(id);
		}
	}

	@Override
	public Iterable<Epi> findAll() {
		return epiRepository.findAll();
	}

	@Override
	public Optional<Epi> findByUuid(String uuid) {
		return epiRepository.findById(uuid);
	}
}
