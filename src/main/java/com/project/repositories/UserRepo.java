package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.User;
import com.project.module.dto.UserDto;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByEnrollmentAndPassword(String enrollment, String password);
}
