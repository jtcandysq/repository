package com.itcast.main.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.main.dao.system.PermissionRepository;
import com.itcast.main.domain.system.Permission;
import com.itcast.main.domain.system.User;
import com.itcast.main.service.system.PermissionService;

@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {

	@Autowired
	private PermissionRepository permissionRepository;

	@Override
	public List<Permission> findByUser(User user) {
		// 返回所有权限
		if (user.getUsername().equals("admin")) {
			return permissionRepository.findAll();
		} else {
			// 根据用户查询
			return permissionRepository.findByUser(user.getId());
		}
	}

}
