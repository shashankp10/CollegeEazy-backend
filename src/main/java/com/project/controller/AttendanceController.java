package com.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.Attendance;
import com.project.module.dto.AttendanceDto;
import com.project.services.AttendanceService;

@RestController
@RequestMapping("/collegeazy/attendance")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	@PostMapping("/create")
	public ResponseEntity<AttendanceDto> createUserAttendance(@RequestBody AttendanceDto attendanceDto){
		AttendanceDto createUserAttendanceDto = this.attendanceService.createUserAttendance(attendanceDto);
		System.out.println("Created successfully!!");
		return new ResponseEntity<>(createUserAttendanceDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/get/{enrollment}")
	public ResponseEntity<Attendance> getUserByEnrollment(@PathVariable Integer enrollment){
		return ResponseEntity.ok(this.attendanceService.findByEnrollment(enrollment));
	}
	
	@PutMapping("/update/{enrollment}")
	public ResponseEntity<AttendanceDto> updateUser(@RequestBody AttendanceDto attendanceDto, @PathVariable("enrollment")Integer enrollment){
		AttendanceDto updateUser = this.attendanceService.updateUserAttendance(attendanceDto, enrollment);
		System.out.println("Detail has been updated succesfully!!");
		return ResponseEntity.ok(updateUser);
	}
}
