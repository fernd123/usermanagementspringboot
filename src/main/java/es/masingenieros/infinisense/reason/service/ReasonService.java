package es.masingenieros.infinisense.reason.service;

import java.util.List;
import java.util.Optional;

import es.masingenieros.infinisense.reason.Reason;

public interface ReasonService {

	Reason save(Reason reason);
	Reason update(String id, Reason reason);
	void deleteReasonById(List<String> reasonUuids);
	Iterable<Reason> findAll();
	Optional<Reason> getReasonByUuid(String uuid);
}
