package com.project.services;

import java.util.List;

import com.project.entities.Attendance;
import com.project.module.dto.AttendanceDto;

public interface AttendanceService {
	
	AttendanceDto createUserAttendance(AttendanceDto attendanceDto);
	AttendanceDto updateUserAttendance(AttendanceDto attendanceDto, int enrollment);
	//AttendanceDto getUserById(int enrollment);
	Attendance findByEnrollment(int enrollment);
	List<Object[]> getAll(int enrollment);
	//AttendanceDto updateUserAttendance(int enrollment);
}
