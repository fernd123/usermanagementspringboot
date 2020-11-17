package es.masingenieros.infinisense.reason.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.reason.Reason;
import es.masingenieros.infinisense.reason.service.ReasonService;
import es.masingenieros.infinisense.reason.service.repository.ReasonRepository;

@Service
public class ReasonServiceImpl implements ReasonService{

	@Autowired
	ReasonRepository reasonRepository;
	
	@Override
	public Reason save(Reason reason) {
		return reasonRepository.save(reason);
	}

	@Override
	public Reason update(String uuid, Reason reason) {
		Optional<Reason> optReason = reasonRepository.findById(uuid);
		Reason reasonInDB = optReason.get();
		reasonInDB.setName(reason.getName());
		reasonInDB.setDescription(reason.getDescription());
		reasonInDB.setActive(reason.getActive());
		return reasonRepository.save(reasonInDB);
	}

	@Override
	public void deleteReasonById(List<String> userUuids) {
		for (String id : userUuids) {
			reasonRepository.deleteById(id);
		}
	}

	@Override
	public Iterable<Reason> findAll() {
		return reasonRepository.findAll();
	}

	@Override
	public Optional<Reason> getReasonByUuid(String uuid) {
		return reasonRepository.findById(uuid);
	}
}
