package es.masingenieros.infinisense.visit.service;

import es.masingenieros.infinisense.visit.Visit;

public interface VisitService{
	Visit save(Visit visit);
	Iterable<Visit> findAll(String filter);
	Visit update(String uuid);
}