package es.masingenieros.infinisense.user.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.UserSignature;
import es.masingenieros.infinisense.user.repository.UserRepository;
import es.masingenieros.infinisense.user.repository.UserSignatureRepository;
import es.masingenieros.infinisense.user.service.UserService;

/* Capa servicio = hacer operaciones de negocio */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private UserSignatureRepository userSignatureRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	@Transactional
	public void deleteUsersByUuid(List<String> userUuids) {
		
	}

	@Override
	public User save(User user) {
		if(user.getPassword() != null) {
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		}
		user.setActive(true);
		return userRepository.save(user);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}

	@Override
	public Optional<User> getUserByDni(String dni) {
		return userRepository.findByDni(dni);
	}

	@Override
	public UserSignature saveSignature(UserSignature usignature) {
		return userSignatureRepository.save(usignature);
	}

	@Override
	public Optional<User> getUserByUuid(String uuid) {
		return userRepository.findById(uuid);
	}

	@Override
	public UserSignature getSignatureByUser(User user) {
		return userSignatureRepository.findByUser(user);
	}
	
	
}
