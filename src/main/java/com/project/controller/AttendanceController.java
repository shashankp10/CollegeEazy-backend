package com.project.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.module.dto.AttendanceDto;
import com.project.payload.ApiResponse;
import com.project.security.JWTUtils;
import com.project.services.AttendanceService;


@RestController
@RequestMapping("/attendance/private")
@CrossOrigin(origins = "http://localhost:3000")
public class AttendanceController {
	
	@Autowired
	private AttendanceService attendanceService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	/*
	@PreAuthorize(value = "hasRole('ROLE_USER')")		
	@PostMapping("/create")
	public ResponseEntity<AttendanceDto> createUserAttendance(@RequestBody AttendanceDto attendanceDto){
		AttendanceDto createUserAttendanceDto = this.attendanceService.createUserAttendance(attendanceDto);
		System.out.println("Created successfully!!");
		return new ResponseEntity<>(createUserAttendanceDto, HttpStatus.CREATED);
	}*/
	
	@PreAuthorize(value = "hasRole('ROLE_USER')")		
	@PostMapping("/create")
	public ResponseEntity<AttendanceDto> createUserAttendance(@RequestHeader("Authorization") String authorizationHeader){
		String token = authorizationHeader.substring(7); 
	    String enrollment = jwtUtils.getEnrollmentFromToken(token);
		AttendanceDto createUserAttendanceDto = this.attendanceService.createUserAttendance(enrollment);
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
	
	@PreAuthorize(value = "hasRole('ROLE_USER')") 
	@GetMapping(value="/")
	public ResponseEntity<Map<String, Object>> getAttendance(@RequestHeader("Authorization") String authorizationHeader){
		String token = authorizationHeader.substring(7); 
	    String enrollment = jwtUtils.getEnrollmentFromToken(token);
	    /*
	    if (!attendanceService.doesEnrollmentExist(enrollment)) {
//	        AttendanceDto attendanceDto = new AttendanceDto();
//	        attendanceDto.setEnrollment(enrollment);
	        ResponseEntity<AttendanceDto> createResponse = createUserAttendance(enrollment);
	        if (createResponse.getStatusCode() != HttpStatus.CREATED) {
	            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	        }
	        enrollment = createResponse.getBody().getEnrollment();
	    }
	    return ResponseEntity.ok(attendanceService.getData(enrollment));
	    */
	    if (!attendanceService.doesEnrollmentExist(enrollment)) {
	        AttendanceDto attendanceDto = new AttendanceDto();
	        attendanceDto.setEnrollment(enrollment);
	        attendanceService.createUserAttendance(enrollment); // Just create the user here, no need to capture the response
	    }

	    return ResponseEntity.ok(attendanceService.getData(enrollment));
	}
	
	@PreAuthorize(value = "hasRole('ROLE_USER')") 
	@DeleteMapping("/delete/{uid}")
	public ResponseEntity<ApiResponse> deleteAttenfance(@PathVariable int uid){
		this.attendanceService.deleteUser(uid);
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);

	}
	
	@PreAuthorize(value = "hasRole('ROLE_USER')")
	@PutMapping("/update/{subjectNumber}/{present}/{absent}")
    public ResponseEntity<String> updateAttendance(
    	@RequestHeader("Authorization") String authorizationHeader,
        @PathVariable("subjectNumber") int subjectNumber,
        @PathVariable("present") int present,
        @PathVariable("absent") int absent) {
		
		String token = authorizationHeader.substring(7); 
	    String enrollment = jwtUtils.getEnrollmentFromToken(token);
	    System.out.println(enrollment);
        attendanceService.updateAttendance(enrollment, subjectNumber, present, absent);
        return ResponseEntity.ok("Attendance updated successfully");
    }
	
	
}
