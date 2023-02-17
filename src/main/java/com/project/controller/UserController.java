package com.project.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.payload.ApiResponse;
import com.project.payload.UserDto;
import com.project.services.UserService;

@RestController
@RequestMapping("/collegeazy") 
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@PostMapping("/register")
	public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
		UserDto createUserDto = this.userService.createUser(userDto);
		return new ResponseEntity<>(createUserDto, HttpStatus.CREATED);
	}
	
	@PutMapping("/users/{userId}")
	public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto, @PathVariable("userId")Long uid){
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
