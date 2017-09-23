package com.itcast.main.service.system;

import java.util.List;

import com.itcast.main.domain.system.Role;
import com.itcast.main.domain.system.User;

public interface RoleService {

	List<Role> findByUser(User user);

}
