package com.project.services;

import java.util.List;

import com.project.entities.Attendance;
import com.project.module.dto.AttendanceDto;

public interface AttendanceService {
	
	AttendanceDto createUserAttendance(AttendanceDto attendanceDto);
	Attendance findByEnrollment(int enrollment);
	List<Object[]> getAll(int enrollment);
	void deleteUser(int uid);
	void updateAttendance(int enrollment, int subjectNumber, int present, int absent);
	
}
