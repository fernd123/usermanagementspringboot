package es.masingenieros.infinisense.visit.service.impl;

import java.sql.Timestamp;
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
	public Visit update(String uuid) {
		Optional<Visit> visitOpt = visitRepository.findById(uuid);
		Visit visitInDb = visitOpt.isPresent() ? visitOpt.get() : null;
		if(visitInDb == null) {
			return null;
		}
		Date date = new Date();
		if(visitInDb.getEndDate() == null) {
			visitInDb.setEndDate(new Timestamp(date.getTime()));
		}
		return visitRepository.save(visitInDb);
	}
}
