package com.project.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.project.entities.Attendance;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer>{
	Attendance findByEnrollment(int enrollment);
	
	@Query(value = "SELECT u.semester,u.branch, a.subject1_present, a.subject1_absent,  a.subject2_present, a.subject2_absent, a.subject3_present, a.subject3_absent,  a.subject4_present, a.subject4_absent,  a.subject5_present, a.subject5_absent FROM Users u INNER JOIN Attendance a ON u.enrollment = a.enrollment WHERE u.enrollment = :enrollment", nativeQuery = true)
	List<Object[]> getAll(@Param("enrollment") int enrollment);
	
}
