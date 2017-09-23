package com.itcast.main.dao.system;

import org.springframework.data.jpa.repository.JpaRepository;

import com.itcast.main.domain.system.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByUsername(String username);

}
