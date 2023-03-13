package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.module.dto.AttendanceDto;
import com.project.payload.ApiResponse;
import com.project.services.AttendanceService;

@RestController
@RequestMapping("/collegeazy/attendance")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
			
	@PostMapping("/create")
	public ResponseEntity<AttendanceDto> createUserAttendance(@RequestBody AttendanceDto attendanceDto){
		AttendanceDto createUserAttendanceDto = this.attendanceService.createUserAttendance(attendanceDto);
		System.out.println("Created successfully!!");
		return new ResponseEntity<>(createUserAttendanceDto, HttpStatus.CREATED);
	}
	
	@GetMapping("/{enrollment}")
	public ResponseEntity<List<Object[]>> getAll(@PathVariable Integer enrollment){
		// validation -> for null check
		
		return ResponseEntity.ok(this.attendanceService.getAll(enrollment));
	}
	
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<ApiResponse> deleteNotes(@PathVariable int uid){
		this.attendanceService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);

	}
	
	@PutMapping("/update/{enrollment}/{subjectNumber}/{present}/{absent}")
    public ResponseEntity<String> updateAttendance(
        @PathVariable("enrollment") int enrollment,
        @PathVariable("subjectNumber") int subjectNumber,
        @PathVariable("present") int present,
        @PathVariable("absent") int absent) {
		
        attendanceService.updateAttendance(enrollment, subjectNumber, present, absent);
        return ResponseEntity.ok("Attendance updated successfully");
    }
	
	
}
