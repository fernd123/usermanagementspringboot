package es.masingenieros.infinisense.reason.service;

import java.util.List;
import java.util.Optional;

import javax.mail.internet.AddressException;

import es.masingenieros.infinisense.reason.Reason;

public interface ReasonService {

	Reason save(Reason reason);
	Reason update(String uuid, Reason reason);
	void deleteReasonById(List<String> reasonUuids);
	Iterable<Reason> findAll();
	Optional<Reason> findReasonByUuid(String uuid);
	boolean createProject(List<String> emailList, List<String> companyList, String reasonUuid) throws AddressException, Exception;
}
