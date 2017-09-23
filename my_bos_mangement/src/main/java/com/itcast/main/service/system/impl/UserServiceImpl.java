package com.itcast.main.service.system.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.main.dao.system.UserRepository;
import com.itcast.main.domain.system.User;
import com.itcast.main.service.system.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
	@Override
	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

}
