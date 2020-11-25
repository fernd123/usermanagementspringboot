package es.masingenieros.infinisense.epis.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.epis.Epi;
import es.masingenieros.infinisense.reason.Reason;

@Repository
public interface EpiRepository extends PagingAndSortingRepository<Epi, String> {
	Optional<Reason> findByName(String name);
}
