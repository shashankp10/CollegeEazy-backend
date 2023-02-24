package com.project.services;

import com.project.entities.Attendance;
import com.project.module.dto.AttendanceDto;

public interface AttendanceService {
	
	AttendanceDto createUserAttendance(AttendanceDto attendanceDto);
	AttendanceDto updateUserAttendance(AttendanceDto attendanceDto, int enrollment);
	AttendanceDto getUserById(int enrollment);
	Attendance findByEnrollment(int enrollment);
	//AttendanceDto updateUserAttendance(int enrollment);
}
