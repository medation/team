package com.sbc.authentication.service.implement;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sbc.authentication.model.User;
import com.sbc.authentication.repository.UserRepository;
import com.sbc.authentication.service.IUserService;

import java.util.Optional;

@Service("userService")
public class UserService implements IUserService{

	@Autowired
	private UserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public User findByUsernameOrEmail(String usernameOrEmail) {
		try {
			User user = userRepository.findByUsernameOrEmail(usernameOrEmail);
			return user;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	@Transactional(readOnly = true)
	public Optional<User> findById(Long id){
		Optional<User> user = userRepository.findById(id);
		return user;
	}

	@Override
	public boolean UserNameOrEmailExists(String usernameOrEmail) {
		User user = userRepository.findByUsernameOrEmail(usernameOrEmail);
		if(user == null) return false;
		else return true;
	}

	@Override
	public void save(User user) {
		userRepository.save(user);
	}

}
