package com.project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.project.entities.User;
import com.project.module.dto.UserDto;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByEnrollmentAndPassword(String enrollment, String password);
	User findByEnrollment(String enrollment);
	
	@Query("SELECT salt FROM User where enrollment = :enrollment")
	String getSaltByEnrollment(String enrollment);
//	@Query("SELECT u.enrollment FROM User u")
//	List<String> getAllEnrollments();
}
