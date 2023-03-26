package com.project.controller;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
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

import com.project.entities.User;
import com.project.exceptions.ErrorResponse;
import com.project.module.dto.UserDto;
import com.project.module.dto.UserLogin;
import com.project.module.dto.UserRegister;
import com.project.payload.ApiResponse;
import com.project.services.UserService;


@RestController
@RequestMapping("/collegeazy") 
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<?> createUser(@RequestBody UserDto userDto) throws NoSuchAlgorithmException{
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
	    if(registerUser!=null) {      
	    	return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Enrollment already exists!!"));
	    }
	    else { 
	    	 String salt = UUID.randomUUID().toString();
	         // Encrypt the password using the salt
	         String encryptedPassword = encryptPassword(userDto.getPassword(), salt);
	         userDto.setSalt(salt);
	         userDto.setPassword(encryptedPassword);
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
		
	}
	private String encryptPassword(String password, String salt) throws NoSuchAlgorithmException {
	    MessageDigest md = MessageDigest.getInstance("SHA-256");
	    String text = password + salt;
	    md.update(text.getBytes(StandardCharsets.UTF_8));
	    byte[] digest = md.digest();
	    String encryptedPassword = Base64.getEncoder().encodeToString(digest);
	    return encryptedPassword;
	}
		// clicking on --> Create an account

	@PostMapping("/login")
	public ResponseEntity<?> login(@RequestBody UserDto userDto) throws NoSuchAlgorithmException{
		
		if(StringUtils.isAnyBlank(userDto.getEnrollment(),userDto.getPassword())) {
			return ResponseEntity
					.status(HttpStatus.BAD_REQUEST)
					.body(new ErrorResponse("Enrollment or password cannot be empty!!"));	    
		}
		String salt = userService.getSaltByEnrollment(userDto.getEnrollment());
	
		if(userService.findByEnrollementAndPassword(userDto.getEnrollment(), encryptPassword(userDto.getPassword(), salt)) == null) {					
			return ResponseEntity
					.status(HttpStatus.NOT_FOUND)
					.body(new ErrorResponse("Invalid credentials"));

		}
		
		User loginUser = userService.findByEnrollementAndPassword(userDto.getEnrollment(), encryptPassword(userDto.getPassword(), salt));		
		if(loginUser!=null) {
			return ResponseEntity
					.status(HttpStatus.OK)
					.body(new ErrorResponse("Logged in!!"));

		}
		return ResponseEntity
				.status(HttpStatus.NOT_FOUND)
				.body(new ErrorResponse("Invalid credentials"));
		
	} 
	
		// Update related setting
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
	
	@DeleteMapping("/users/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Long uid){
		this.userService.deleteUser(uid);;
		return new ResponseEntity<ApiResponse>(new ApiResponse("user deleted successfully", true),HttpStatus.OK);
	}

	@GetMapping("/users")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		return ResponseEntity.ok(this.userService.getAllUser());
	}
	 

	@GetMapping("/users/{userId}")
	public ResponseEntity<UserDto> getSingleUser(@PathVariable Long userId){
		return ResponseEntity.ok(this.userService.getUserById(userId));
	}
	
}
