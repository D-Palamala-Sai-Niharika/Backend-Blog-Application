package com.application.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.application.blog.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {
	
}
