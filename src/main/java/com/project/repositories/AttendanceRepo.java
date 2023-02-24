package com.project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.project.entities.Attendance;

public interface AttendanceRepo extends JpaRepository<Attendance, Integer>{
	Attendance findByEnrollment(int enrollment);
}
