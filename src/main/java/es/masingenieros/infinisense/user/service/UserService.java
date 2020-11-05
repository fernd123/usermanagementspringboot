/**
 * Copyright (C) 2019, BuyPower SL
 * All rights reserved.
 */
package es.masingenieros.infinisense.user.service;

import java.util.List;

import es.masingenieros.infinisense.user.User;

/**
 * @author Fernando Rodriguez
 *
 */
public interface UserService {

	User save(User user);
	void deleteUsersByUuid(List<String> userUuids);
	Iterable<User> findAll();
}
