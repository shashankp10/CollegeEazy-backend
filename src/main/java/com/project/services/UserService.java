package com.project.services;

import java.util.List;

import com.project.payload.UserDto;

public interface UserService {
	
	UserDto createUser(UserDto user);
	UserDto updateUser(UserDto user, Long userId);
	UserDto getUserById(Long userId);
	List<UserDto> getAllUser();
	void deleteUser(Long userId);
}
