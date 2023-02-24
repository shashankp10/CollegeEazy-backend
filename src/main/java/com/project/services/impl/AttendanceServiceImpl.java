package com.project.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.project.entities.Attendance;
import com.project.exceptions.ResourceNotFoundException;
import com.project.module.dto.AttendanceDto;
import com.project.repositories.AttendanceRepo;
import com.project.services.AttendanceService;

@Service
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@Override
	public AttendanceDto createUserAttendance(AttendanceDto attendanceDto) {
		Attendance attendance =  this.dtoToAttendance(attendanceDto);
		Attendance saved = this.attendanceRepo.save(attendance);
		return this.attendanceToDto(saved);
	}

	@Override
	public AttendanceDto updateUserAttendance(AttendanceDto attendanceDto, int enrollment) {
		
		Attendance attendance = this.attendanceRepo.findByEnrollment(enrollment);
//				.orElseThrow(() -> new ResourceNotFoundException("Enrollment","Id",enrollment));
		
		attendance.setSubject1(attendanceDto.getSubject1());
		attendance.setSubject2(attendanceDto.getSubject2());
		attendance.setSubject3(attendanceDto.getSubject3());
		attendance.setSubject4(attendanceDto.getSubject4());
		attendance.setSubject5(attendanceDto.getSubject5());
		
		Attendance updatedUser = this.attendanceRepo.save(attendance);
		AttendanceDto attendanceDto1 = this.attendanceToDto(updatedUser);
		return attendanceDto1;
	}

	@Override
	public AttendanceDto getUserById(int enrollment) {
		Attendance attendance = this.attendanceRepo.findById(enrollment)
				.orElseThrow(() -> new ResourceNotFoundException("Enrollment"," id ",enrollment));
		
		return this.attendanceToDto(attendance);
	}

	private Attendance dtoToAttendance(AttendanceDto attendanceDto) {
		Attendance attendance = new Attendance();
		attendance.setEnrollment(attendanceDto.getEnrollment());
		attendance.setId(attendanceDto.getId());
		attendance.setSubject1(attendanceDto.getSubject1());
		attendance.setSubject2(attendanceDto.getSubject2());
		attendance.setSubject3(attendanceDto.getSubject3());
		attendance.setSubject4(attendanceDto.getSubject4());
		attendance.setSubject5(attendanceDto.getSubject5());
		return attendance;
	}
	private AttendanceDto attendanceToDto(Attendance attendance) {
		AttendanceDto attendanceDto = new AttendanceDto();
		attendanceDto.setEnrollment(attendance.getEnrollment());
		attendanceDto.setId(attendance.getId());
		attendanceDto.setSubject1(attendance.getSubject1());
		attendanceDto.setSubject2(attendance.getSubject2());
		attendanceDto.setSubject3(attendance.getSubject3());
		attendanceDto.setSubject4(attendance.getSubject4());
		attendanceDto.setSubject5(attendance.getSubject5());
		return attendanceDto;
	}
	@Override
	public Attendance findByEnrollment(int enrollment) {
		return attendanceRepo.findByEnrollment(enrollment);
	}
}
