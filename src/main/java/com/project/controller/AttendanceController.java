package com.project.controller;

import java.util.List;
import java.util.Map;

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

import com.project.entities.Attendance;
import com.project.entities.Notes;
import com.project.module.dto.AttendanceDto;
import com.project.payload.ApiResponse;
import com.project.services.AttendanceService;
import com.project.services.impl.AttendanceServiceImpl;

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
	
//	@GetMapping("/{enrollment}")
//	public ResponseEntity<List<Object[]>> getAll(@PathVariable Integer enrollment){
//		// validation -> for null check
//		if(enrollment == null || attendanceService.findByEnrollment(enrollment) == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
//		return ResponseEntity.ok(this.attendanceService.getAll(enrollment));
//	}
	@GetMapping(value="/{enrollment}")
	public ResponseEntity<Map<String, Object>> getNotes(@PathVariable String enrollment){
//		if(enrollment == null) {
//			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//		}
		
		return ResponseEntity.ok(attendanceService.getData(enrollment));
	}
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<ApiResponse> deleteNotes(@PathVariable int uid){
		this.attendanceService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);

	}
	
	@PutMapping("/update/{enrollment}/{subjectNumber}/{present}/{absent}")
    public ResponseEntity<String> updateAttendance(
        @PathVariable("enrollment") String enrollment,
        @PathVariable("subjectNumber") int subjectNumber,
        @PathVariable("present") int present,
        @PathVariable("absent") int absent) {
		
        attendanceService.updateAttendance(enrollment, subjectNumber, present, absent);
        return ResponseEntity.ok("Attendance updated successfully");
    }
	
	
}
