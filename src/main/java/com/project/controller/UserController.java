package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.module.dto.UserDto;
import com.project.payload.ApiResponse;
import com.project.security.JWTUtils;
import com.project.services.UserService;


@RestController
@RequestMapping("/private") 
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private JWTUtils jwtUtils;
	
	// Update related setting
		@PreAuthorize(value = "hasRole('ROLE_USER')")
		@PutMapping("/users/{userId}")
		public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId")Long uid){
			if(userDto.getBranch()==null || userDto.getEnrollment()==null || userDto.getPassword()==null
					|| userDto.getName()==null || userDto.getSemester()==0) {
		        // return message for null values
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		    }
			UserDto updateUser = this.userService.updateUser(userDto, uid);
			System.out.println("Detail has been updated succesfully!!");
			return ResponseEntity.ok(updateUser);
		}
		
		@PreAuthorize(value = "hasRole('ROLE_USER')")
		@DeleteMapping("/users/{userId}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long uid){
			this.userService.deleteUser(uid);
			return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);
		}
		
		@PreAuthorize(value = "hasRole('ROLE_USER')") // change to admin 
		@GetMapping("/users")
		public ResponseEntity<List<UserDto>> getAllUsers(){
			System.out.println(userService.getAllUser());
			return ResponseEntity.ok(this.userService.getAllUser());
		}
		 
		@PreAuthorize(value = "hasRole('ROLE_USER')")
		@GetMapping("/user")
		public ResponseEntity<UserDto> getSingleUser(@RequestHeader("Authorization") String authorizationHeader){
			String token = authorizationHeader.substring(7);
		    String enrollment = jwtUtils.getEnrollmentFromToken(token);
			return ResponseEntity.ok(this.userService.getUserById(enrollment));
		}

	
	/*
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) throws NoSuchAlgorithmException{
			// validation Checks!!
		if (StringUtils.isAnyBlank(userDto.getBranch(), userDto.getEnrollment(), userDto.getPassword(), userDto.getName())
	            || userDto.getSemester()==0) {
	        return ResponseEntity
	                .status(HttpStatus.BAD_REQUEST)
	                .body(new ErrorResponse("One or more field(s) is/are empty or null"));
	    }
		if(userDto.getEnrollment().length()!=11 || userDto.getPassword().length()<=4) {
			String message = "Enrollment must be of 11 characters long and password must be"
					+ "atleast 4 characters long!!";
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse(message));
		}
	    User registerUser = userService.findByEnrollment(userDto.getEnrollment());
	    UserDto createUserDto = new UserDto();
	    	// check if enrollment already exists!!
	    if(registerUser!=null) {      
	    	return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Enrollment already exists!!"));
	    }
	    else { 
	         createUserDto = this.userService.createUser(userDto);
	         System.out.println("User registered successfully!!");
	         UserDto responseDto = new UserDto();
	         responseDto.setName(createUserDto.getName());
	         responseDto.setEnrollment(createUserDto.getEnrollment());
	         responseDto.setBranch(createUserDto.getBranch());
	         responseDto.setSemester(createUserDto.getSemester());
	         return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(new ErrorResponse("User registered successfully!!"));
	    }
		
	}*/
		// clicking on --> Create an account
	/*
	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto) throws NoSuchAlgorithmException{
		
		if(StringUtils.isAnyBlank(userDto.getEnrollment(),userDto.getPassword())) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Enrollment or password cannot be empty!!"));	    
		}
		
		if(userService.findByEnrollementAndPassword(userDto.getEnrollment(), userDto.getPassword()) == null) {					
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("Invalid credentials"));

		}
		
		User loginUser = userService.findByEnrollementAndPassword(userDto.getEnrollment(), userDto.getPassword());		
		if(loginUser!=null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ErrorResponse("Logged in!!"));

		}
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse("Invalid credentials"));
		
		
	} */
	
			
}
