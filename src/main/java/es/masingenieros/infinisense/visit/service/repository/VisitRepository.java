package es.masingenieros.infinisense.visit.service.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.visit.Visit;

@Repository
public interface VisitRepository extends PagingAndSortingRepository<Visit, String> {

	//Optional<Reason> findByName(String name);
}
