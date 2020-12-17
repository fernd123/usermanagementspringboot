package es.masingenieros.infinisense.reason.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.masingenieros.infinisense.mail.EmailService;
import es.masingenieros.infinisense.message.Message;
import es.masingenieros.infinisense.message.MessageEnum;
import es.masingenieros.infinisense.message.repository.MessageRepository;
import es.masingenieros.infinisense.mulitenancy.TenantContext;
import es.masingenieros.infinisense.reason.Reason;
import es.masingenieros.infinisense.reason.ReasonProjectEmail;
import es.masingenieros.infinisense.reason.ReasonProjectToken;
import es.masingenieros.infinisense.reason.repository.ReasonProjectEmailRepository;
import es.masingenieros.infinisense.reason.repository.ReasonProjectTokenRepository;
import es.masingenieros.infinisense.reason.repository.ReasonRepository;
import es.masingenieros.infinisense.reason.service.ReasonService;
import es.masingenieros.infinisense.security.CustomUserDetailsService;
import es.masingenieros.infinisense.security.JwtUtil;

@Service
public class ReasonServiceImpl implements ReasonService{

	@Autowired
	ReasonRepository reasonRepository;

	@Autowired
	ReasonProjectEmailRepository reasonProjectEmailRepository;
	
	@Autowired
	ReasonProjectTokenRepository reasonProjectTokenRepository;
	
	@Autowired
	MessageRepository messageRepository;

	@Autowired 
	EmailService emailService;
	
	@Autowired
	private JwtUtil jwtTokenUtil;
	
	@Autowired
	private CustomUserDetailsService userDetailsService;

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
		reasonInDB.setPlantCoordinate(reason.getPlantCoordinate());
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
	public Optional<Reason> findReasonByUuid(String uuid) {
		return reasonRepository.findById(uuid);
	}

	@Override
	public boolean createProject(List<String> emailListReq, List<String> companyList, String reasonUuid) throws Exception {

		// Check if message exists
		Message messageProjectInvitation = messageRepository.findByType(MessageEnum.PROJECTINVITATION);
		
		if(messageProjectInvitation == null) {
			throw new Exception("Message type "+MessageEnum.PROJECTINVITATION+" does not exists");
		}

		Optional<Reason> reasonOpt = reasonRepository.findById(reasonUuid);
		Reason reason = reasonOpt.get();
		List<String> emailList = new ArrayList<String>();
		// Validate emails					
		for (String email : emailListReq) {
			isValidEmailAddress(email);
		}
		
		
		// Check if emails exists for the project in db
		for(String email: emailListReq) {
			if(!reasonProjectEmailRepository.findByReasonAndEmail(reason, email).isPresent()) {
				emailList.add(email);
			}
		}

		for (int i=0; i<emailList.size(); i++) {
			ReasonProjectEmail rp = new ReasonProjectEmail();
			rp.setEmail(emailList.get(i));
			rp.setCompany(companyList.get(i));
			rp.setActive(true);
			rp.setReason(reasonOpt.get());
			reasonProjectEmailRepository.save(rp);

			try {
				// Generate form token
				Map<String, Object> claims = new HashMap<>();
				//claims.put("tenantid", TenantContext.getCurrentTenant());
				claims.put("reasonUuid", reasonUuid);
				claims.put("reasonProjectEmailUuid", rp.getUuid());
				final String jwt = jwtTokenUtil.generateTokenForm(claims);
				
				ReasonProjectToken reasonProjectToken = new ReasonProjectToken();
				reasonProjectToken.setToken(jwt);
				reasonProjectToken.setReason(reason);
				reasonProjectTokenRepository.save(reasonProjectToken);
				
				String url = "http://localhost:4200/user-pages/update-project?token="+reasonProjectToken.getUuid()+"&tenant="+TenantContext.getCurrentTenant();
				String message = messageRepository.findByType(MessageEnum.PROJECTINVITATION).getName();
				message = (message.replace("$name", companyList.get(i)));
				message = (message.replace("$project", reason.getName()));
				message = (message.replace("$url", url));

				emailService.sendSimpleMessage(emailList.get(i), "Proyecto asignado", message);
				rp.setSended(true);
				
			}catch(Exception e) {
				rp.setSended(false);
				e.printStackTrace();
			}finally {
				reasonProjectEmailRepository.save(rp);
			}
		}
		return true;
	}

	private void isValidEmailAddress(String email) throws AddressException {
		InternetAddress emailAddr = new InternetAddress(email);
		emailAddr.validate();

	}
}
