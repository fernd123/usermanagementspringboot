/**
 * Copyright (C) 2019, BuyPower SL
 * All rights reserved.
 */
package es.masingenieros.infinisense.user.service;

import java.util.List;
import java.util.Optional;

import es.masingenieros.infinisense.user.User;
import es.masingenieros.infinisense.user.UserSignature;

/**
 * @author Fernando Rodriguez
 *
 */
public interface UserService {

	User save(User user);
	User update(String uuid, User user);
	void deleteUsersByUuid(List<String> userUuids);
	Iterable<User> findAll();
	Optional<User> getUserByDni(String dni);
	UserSignature saveSignature(UserSignature usignature);
	Optional<User> getUserByUuid(String uuid);
	UserSignature getSignatureByUser(User user);
	Iterable<User> getInternalUsers(String tenantId);
}
