package es.masingenieros.infinisense.reason.service.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.reason.Reason;

@Repository
public interface ReasonRepository extends CrudRepository<Reason, String> {
	Optional<Reason> findByName(String name);
}
