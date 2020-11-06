package es.masingenieros.infinisense.reason.service;

import java.util.List;

import es.masingenieros.infinisense.reason.Reason;

public interface ReasonService {

	Reason save(Reason reason);
	Reason update(String id, Reason reason);
	void deleteReasonById(List<String> reasonUuids);
	Iterable<Reason> findAll();
}
