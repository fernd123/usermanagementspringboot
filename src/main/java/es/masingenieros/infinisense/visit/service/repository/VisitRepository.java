package es.masingenieros.infinisense.visit.service.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMethod;

import es.masingenieros.infinisense.visit.Visit;

@RepositoryRestResource(path = "visit")
@CrossOrigin(origins = "*", methods = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
public interface VisitRepository extends PagingAndSortingRepository<Visit, String> {


	@Query(value="SELECT count(v.uuid) FROM visit as v join reason as r on v.reason_id = r.uuid "
			+ "join plant_coordinates as pc on r.plant_coordinate_id = pc.uuid "
			+ "join plant as p on pc.plant_id = p.uuid "
			+ "where p.uuid = ?1 "
			+ "and DATE(start_date) = CURDATE()",
			nativeQuery = true)
	int todayVisitsByPlant(@Param("plantUuid") String plantUuid);
	
}
