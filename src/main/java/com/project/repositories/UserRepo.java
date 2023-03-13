package com.project.repositories;


import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByEnrollmentAndPassword(String enrollment, String password);
	User findByEnrollment(String enrollment);
	
//	@Query("SELECT u.enrollment FROM User u")
//	List<String> getAllEnrollments();
}
