package com.itcast.main.service.system.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.itcast.main.dao.system.RoleRepository;
import com.itcast.main.domain.system.Role;
import com.itcast.main.domain.system.User;
import com.itcast.main.service.system.RoleService;

@Service
@Transactional
public class RoleServiceImpl implements RoleService {
	
	@Autowired
	private RoleRepository roleRepository;

	@Override
	public List<Role> findByUser(User user) {
		// 基于用户查询角色
		// admin具有所有角色
		if(user.getUsername().equals("admin")){
			return roleRepository.findAll();
		}else{
			return roleRepository.findByUser(user.getId());
		}
		
	}

}
