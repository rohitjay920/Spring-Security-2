package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.project.entity.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	User findByEmail(String username);
}
