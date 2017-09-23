package com.itcast.main.service.system;

import com.itcast.main.domain.system.User;

public interface UserService {

	User findByUsername(String username);

}
