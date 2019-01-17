package com.sbc.authentication.service;

import com.sbc.authentication.model.User;

import java.util.Optional;

public interface IUserService {
	
	void save(User user);
	User findByUsernameOrEmail(String usermaneOrEmail);
	Optional<User> findById(Long id);
	boolean UserNameOrEmailExists(String usernameOrEmail);
	
}
