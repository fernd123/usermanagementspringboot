package es.masingenieros.infinisense.company.service;

import java.util.List;
import java.util.Optional;

import es.masingenieros.infinisense.company.Company;
import es.masingenieros.infinisense.company.service.impl.TenantException.CreateUpdateTenantSchemaException;
import es.masingenieros.infinisense.company.service.impl.TenantException.QueryTenantSchemaException;
import es.masingenieros.infinisense.company.service.impl.TenantException.TenantSchemaAlreadyExistsException;
import es.masingenieros.infinisense.user.User;

public interface CompanyService {

	Company save(Company company);
	Company update(String uuid, Company company);
	void deleteCompanyByUuid(List<String> companyUuids);
	Iterable<Company> findAll();
	Optional<Company> findByUuid(String uuid);
	Company createSchema(Company company, User userDefault) throws CreateUpdateTenantSchemaException, QueryTenantSchemaException, TenantSchemaAlreadyExistsException;
}
