package com.project.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.project.entities.Attendance;
import com.project.entities.User;
import com.project.exceptions.ResourceNotFoundException;
import com.project.module.dto.AttendanceDto;
import com.project.repositories.AttendanceRepo;
import com.project.repositories.UserRepo;
import com.project.services.AttendanceService;

import jakarta.transaction.Transactional;

@Service
public class AttendanceServiceImpl implements AttendanceService{

	@Autowired
	private AttendanceRepo attendanceRepo;
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private UserRepo userRepo;
	
	@Override
	public AttendanceDto createUserAttendance(String enrollment) {
//		int semester = extractSemesterFromEnrollment(enrollment);
//		String branch = extractBranchFromEnrollment(enrollment);
//		List<String> subjectIds =  getSubjectIdsForSemester(semester,branch);
		Attendance attendance = new Attendance();
		attendance.setEnrollment(enrollment);
		//Attendance attendance =  this.dtoToAttendance(attendanceDto);
		Attendance saved = this.attendanceRepo.save(attendance);
		return this.attendanceToDto(saved);
	}
//	private List<String> getSubjectIdsForSemester(int semester, String branch) {
//		List<String> firstYear = new ArrayList<>();
//		firstYear.addAll(Arrays.asList("ES-101", "BS-103", "BS-105","ES-106", "ES-107", "BS-109","BS-111","BS-112", "HS-113", "HS-115","HS-117","ES-119", "BS-114"));
//		return null;
//	}
//	private int extractSemesterFromEnrollment(String enrollment) {
//		User user = userRepo.findByEnrollment(enrollment);
//	    return user.getSemester();
//	}
//	private String extractBranchFromEnrollment(String enrollment) {
//		User user = userRepo.findByEnrollment(enrollment);
//	    return user.getBranch();
//	}
	@Override
	public Attendance findByEnrollment(String enrollment) {
		return attendanceRepo.findByEnrollment(enrollment);
	}
	
	@Override
	public List<Object[]> getAll(String enrollment) {
		List<Object[]> list = this.attendanceRepo.getAll(enrollment);
		return list;
	}
	@Override
	public void deleteUser(int uid) {

		Attendance attendance = this.attendanceRepo.findById(uid)
				.orElseThrow(() -> new ResourceNotFoundException("User","Id",uid));
		
		this.attendanceRepo.delete(attendance);
		// User don't know what the uid... Do something else to delete the row!!
	}
	@Override
	@Transactional
	public void updateAttendance(String enrollment, int subjectNumber, int present, int absent) {
		//attendanceRepo.updateAttendance(enrollment, subjectNumber, present, absent);
		 switch (subjectNumber) {
	        case 1:
	            attendanceRepo.updateSubject1Attendance(enrollment, present, absent);
	            break;
	        case 2:
	            attendanceRepo.updateSubject2Attendance(enrollment, present, absent);
	            break;
	        case 3:
	            attendanceRepo.updateSubject3Attendance(enrollment, present, absent);
	            break;
	        case 4:
	            attendanceRepo.updateSubject4Attendance(enrollment, present, absent);
	            break;
	        case 5:
	            attendanceRepo.updateSubject5Attendance(enrollment, present, absent);
	            break;
	        case 6:
	            attendanceRepo.updateSubject6Attendance(enrollment, present, absent);
	            break;
	        default:
	            throw new IllegalArgumentException("Invalid subjectNumber: " + subjectNumber);
	    }
	}
	
	@Override
    public Map<String, Object> getData(String enrollment) {
		 // Query to get semester and branch from users table
        String userQuery = "SELECT semester, branch FROM users WHERE enrollment = ?";
        Object[] userParams = {enrollment};
        Map<String, Object> userRow = jdbcTemplate.queryForMap(userQuery, userParams);

        // Query to get attendance data from attendance table
        String attendanceQuery = "SELECT subject1, subject1_present, subject1_absent,subject2, subject2_present, subject2_absent, subject3, subject3_present, subject3_absent,subject4, subject4_present, subject4_absent,subject5, subject5_present, subject5_absent,subject6, subject6_present, subject6_absent FROM attendance WHERE enrollment = ?";
        Object[] attendanceParams = {enrollment};
        List<Map<String, Object>> attendanceRows = jdbcTemplate.queryForList(attendanceQuery, attendanceParams);
        // Create the final map with the desired format
        Map<String, Object> result = new HashMap<>();
        result.put("semester", userRow.get("semester"));
        result.put("branch", userRow.get("branch"));
        
        result.put("subject1", attendanceRows.get(0).get("subject1"));
        result.put("subject1_present", attendanceRows.get(0).get("subject1_present"));
        result.put("subject1_absent", attendanceRows.get(0).get("subject1_absent"));
        
        result.put("subject2", attendanceRows.get(0).get("subject2"));
        result.put("subject2_present", attendanceRows.get(0).get("subject2_present"));
        result.put("subject2_absent", attendanceRows.get(0).get("subject2_absent"));
        
        result.put("subject3", attendanceRows.get(0).get("subject3"));
        result.put("subject3_present", attendanceRows.get(0).get("subject3_present"));
        result.put("subject3_absent", attendanceRows.get(0).get("subject3_absent"));
        
        result.put("subject4", attendanceRows.get(0).get("subject4"));
        result.put("subject4_present", attendanceRows.get(0).get("subject4_present"));
        result.put("subject4_absent", attendanceRows.get(0).get("subject4_absent"));
        
        result.put("subject5", attendanceRows.get(0).get("subject5"));
        result.put("subject5_present", attendanceRows.get(0).get("subject5_present"));
        result.put("subject5_absent", attendanceRows.get(0).get("subject5_absent"));
        
        result.put("subject6", attendanceRows.get(0).get("subject6"));
        result.put("subject6_present", attendanceRows.get(0).get("subject6_present"));
        result.put("subject6_absent", attendanceRows.get(0).get("subject6_absent"));
       
        return result;
    }
	/*
	@Override
	public AttendanceDto getUserById(int enrollment) {
		Attendance attendance = this.attendanceRepo.findById(enrollment)
				.orElseThrow(() -> new ResourceNotFoundException("Enrollment"," id ",enrollment));
		
		return this.attendanceToDto(attendance);
	}
	*/
	private Attendance dtoToAttendance(AttendanceDto attendanceDto) {
		Attendance attendance = new Attendance();
		attendance.setEnrollment(attendanceDto.getEnrollment());
		attendance.setId(attendanceDto.getId());
		
		attendance.setSubject1(attendanceDto.getSubject1());
		attendance.setSubject1_present(attendanceDto.getSubject1_present());
		attendance.setSubject1_absent(attendanceDto.getSubject1_absent());
		
		attendance.setSubject2(attendanceDto.getSubject2());
		attendance.setSubject2_present(attendanceDto.getSubject2_present());
		attendance.setSubject2_absent(attendanceDto.getSubject2_absent());
		
		attendance.setSubject3(attendanceDto.getSubject3());
		attendance.setSubject3_present(attendanceDto.getSubject3_present());
		attendance.setSubject3_absent(attendanceDto.getSubject3_absent());
		
		attendance.setSubject4(attendanceDto.getSubject4());
		attendance.setSubject4_present(attendanceDto.getSubject4_present());
		attendance.setSubject4_absent(attendanceDto.getSubject4_absent());
		
		attendance.setSubject5(attendanceDto.getSubject5());
		attendance.setSubject5_present(attendanceDto.getSubject5_present());
		attendance.setSubject5_absent(attendanceDto.getSubject5_absent());
		
		attendance.setSubject6(attendanceDto.getSubject6());
		attendance.setSubject6_present(attendanceDto.getSubject6_present());
		attendance.setSubject6_absent(attendanceDto.getSubject6_absent());
		return attendance;
	}
	private AttendanceDto attendanceToDto(Attendance attendance) {
		AttendanceDto attendanceDto = new AttendanceDto();
		attendanceDto.setEnrollment(attendance.getEnrollment());
		attendanceDto.setId(attendance.getId());
		
		attendanceDto.setSubject1(attendance.getSubject1());
		attendanceDto.setSubject1_present(attendance.getSubject1_present());
		attendanceDto.setSubject1_absent(attendance.getSubject1_absent());
		
		attendanceDto.setSubject2(attendance.getSubject2());
		attendanceDto.setSubject2_present(attendance.getSubject2_present());
		attendanceDto.setSubject2_absent(attendance.getSubject2_absent());
		
		attendanceDto.setSubject3(attendance.getSubject3());
		attendanceDto.setSubject3_present(attendance.getSubject3_present());
		attendanceDto.setSubject3_absent(attendance.getSubject3_absent());
		
		attendanceDto.setSubject4(attendance.getSubject4());
		attendanceDto.setSubject4_present(attendance.getSubject4_present());
		attendanceDto.setSubject4_absent(attendance.getSubject4_absent());
		
		attendanceDto.setSubject5(attendance.getSubject5());
		attendanceDto.setSubject5_present(attendance.getSubject5_present());
		attendanceDto.setSubject5_absent(attendance.getSubject5_absent());
		
		attendanceDto.setSubject6(attendance.getSubject6());
		attendanceDto.setSubject6_present(attendance.getSubject6_present());
		attendanceDto.setSubject6_absent(attendance.getSubject6_absent());
		
		return attendanceDto;
	}
	
		
	
}
