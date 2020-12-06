package es.masingenieros.infinisense.company.repository;

import java.util.Optional;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import es.masingenieros.infinisense.company.Company;

@Repository
public interface CompanyRepository extends PagingAndSortingRepository<Company, String> {
	Optional<Company> findByName(String name);
}
