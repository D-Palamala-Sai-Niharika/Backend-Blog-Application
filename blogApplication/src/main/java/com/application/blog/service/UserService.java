package com.application.blog.service;

import java.util.List;

import com.application.blog.payloads.UserDto;

public interface UserService {
	//create user
	UserDto createUser(UserDto userDto);
	//get all users
	List<UserDto> getAllUsers();
	//get user by id
	UserDto getUserById(Integer id);
	//update user
	UserDto updateUser(UserDto userDto, Integer id);
	//delete user
	void deleteUser(Integer id);
}
