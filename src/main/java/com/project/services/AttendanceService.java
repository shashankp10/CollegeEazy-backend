package com.project.services;

import java.util.List;
import java.util.Map;

import com.project.entities.Attendance;
import com.project.module.dto.AttendanceDto;

public interface AttendanceService {
	
	AttendanceDto createUserAttendance(String enrollment);
	Attendance findByEnrollment(String enrollment);
	List<Object[]> getAll(String enrollment);
	void deleteUser(int uid);
	void updateAttendance(AttendanceDto attendanceDto);
	Map<String, Object> getData(String enrollment);
	boolean doesEnrollmentExist(String enrollment);
}
