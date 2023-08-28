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
	void updateAttendance(String enrollment, int subjectNumber, int present, int absent);
	Map<String, Object> getData(String enrollment);
	Attendance doesEnrollmentExist(String enrollment);
}
