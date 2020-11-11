package es.masingenieros.infinisense.visit.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.visit.Visit;
import es.masingenieros.infinisense.visit.service.VisitService;
import es.masingenieros.infinisense.visit.service.repository.VisitRepository;

@Service
public class VisitServiceImpl implements VisitService{

	@Autowired
	VisitRepository visitRepository;
	
	@Override
	public Visit save(Visit visit) {
		return visitRepository.save(visit);
	}

	@Override
	public Iterable<Visit> findAll() {
		return visitRepository.findAll();
	}
}
