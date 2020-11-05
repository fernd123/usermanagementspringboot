package es.masingenieros.infinisense.user.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.repository.UserRepository;
import es.masingenieros.infinisense.user.service.UserService;

/* Capa servicio = hacer operaciones de negocio */
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	
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
		return userRepository.save(user);
	}

	@Override
	public Iterable<User> findAll() {
		return userRepository.findAll();
	}
}
