package com.itcast.main.service.system;

import java.util.List;

import com.itcast.main.domain.system.Permission;
import com.itcast.main.domain.system.User;

public interface PermissionService {

	List<Permission> findByUser(User user);

}
