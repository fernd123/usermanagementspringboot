package es.masingenieros.infinisense.visit.service.impl;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
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
	public Iterable<Visit> findAll(String filter) {
		//TODO: Filtrar por fechas
		Iterable<Visit> visitList = visitRepository.findAll(Sort.by(Sort.Direction.DESC, "startDate"));
		return visitList;
	}

	@Override
	public Visit update(Visit visit) {
		Optional<Visit> visitOpt = visitRepository.findById(visit.getUuid());
		Visit visitInDb = visitOpt.isPresent() ? visitOpt.get() : null;
		if(visitInDb == null) {
			return null;
		}
		if(visit.getEndDate() != null) {
			visitInDb.setEndDate(visit.getEndDate());
		}
		
		if(visit.getEpis() != null) {
			visitInDb.setEpis(visit.getEpis());
		}
		
		if(visitInDb.getFirstname() == null) {			
			visitInDb.setFirstname(visit.getFirstname());
		}
		if(visitInDb.getLastname() == null) {			
			visitInDb.setLastname(visit.getLastname());
		}
		if(visitInDb.getDni() == null) {			
			visitInDb.setDni(visit.getDni());
		}
		if(visitInDb.getSignature() == null) {			
			visitInDb.setSignature(visit.getSignature());		
		}
		return visitRepository.save(visitInDb);
	}

	@Override
	public Optional<Visit> findByUuid(String uuid) {
		return visitRepository.findById(uuid);
	}
}
