package es.masingenieros.infinisense.visit.service;

import java.util.Optional;

import es.masingenieros.infinisense.visit.Visit;

public interface VisitService{
	Visit save(Visit visit);
	Iterable<Visit> findAll(String filter);
	Visit update(Visit visit);
	Optional<Visit> findByUuid(String uuid);
}