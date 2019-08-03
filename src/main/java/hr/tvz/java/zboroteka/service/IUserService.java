package hr.tvz.java.zboroteka.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hr.tvz.java.zboroteka.model.User;
import hr.tvz.java.zboroteka.repository.UserRepository;
import hr.tvz.java.zboroteka.service.impl.UserService;

@Service
public class IUserService implements UserService {
	@Autowired
	UserRepository userRepository;

	@Override
	public User findById(Integer creatorId) {
		Optional<User> user = userRepository.findById(creatorId);
		return user.isPresent() ? user.get() : null;
	}

}
