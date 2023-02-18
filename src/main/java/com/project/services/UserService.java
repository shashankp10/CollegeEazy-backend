package com.project.services;

import java.util.List;

import com.project.entities.User;
import com.project.module.dto.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Long userId);
	UserDto getUserById(Long userId);
	List<UserDto> getAllUser();
	void deleteUser(Long userId);
	User findByEnrollementAndPassword(String enrollment, String password);
}
