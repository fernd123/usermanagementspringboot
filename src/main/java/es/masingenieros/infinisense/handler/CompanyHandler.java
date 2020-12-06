package es.masingenieros.infinisense.handler;

import java.util.Optional;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.HandleBeforeSave;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.stereotype.Component;

import es.masingenieros.infinisense.company.Company;
import es.masingenieros.infinisense.company.repository.CompanyRepository;
import es.masingenieros.infinisense.exceptions.FieldException;

@RepositoryEventHandler(Company.class) 
@Component
public class CompanyHandler {

	@Autowired
	private CompanyRepository companyRepository;

	Logger logger = Logger.getLogger("Class Company");

	@HandleBeforeCreate
	public void handleAuthorBeforeCreate(Company company) throws FieldException{
		logger.info("Inside Company Before Create....");
		this.validateSave(company);
	}

	@HandleBeforeSave
	public void handleAuthorBeforeSave(Company company) throws FieldException{
		logger.info("Inside Company Before Save....");
		this.validateSave(company);
	}
	

	private void validateSave(Company company) throws FieldException {
		boolean isNew = company.getUuid() == null;
		Optional<Company> companyByName = companyRepository.findByName(company.getName());
		boolean companyExists = companyByName.isPresent();
		String uuid = companyExists ? companyByName.get().getUuid() : null;
		if ( (isNew && companyExists) || (companyExists && !uuid.equals(company.getUuid()))) {
			throw new FieldException(ValidationConstants.MSG_VALUE_MUST_BE_UNIQUE, "name", "company");
		}
	}
}
