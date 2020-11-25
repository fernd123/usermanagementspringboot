package es.masingenieros.infinisense.epis.service;

import java.util.List;
import java.util.Optional;

import es.masingenieros.infinisense.epis.Epi;

public interface EpiService {

	Epi save(Epi epi);
	Epi update(String uuid, Epi epi);
	void deleteEpisByUuid(List<String> epiUuids);
	Iterable<Epi> findAll();
	Optional<Epi> findByUuid(String uuid);
}
