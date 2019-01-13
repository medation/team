package com.sbc.authentication.service;

import com.sbc.authentication.model.User;

public interface IUserService {
	
	void save(User user);
	User findByUsernameOrEmail(String usermaneOrEmail);
	User findById(int id);
	boolean UserNameOrEmailExists(String usernameOrEmail);
	
}
