package com.project.controller.auth;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.project.entities.User;
import com.project.exceptions.ErrorResponse;
import com.project.module.dto.AuthenticationRequest;
import com.project.module.dto.UserDto;
import com.project.security.JWTUtils;
import com.project.security.JpaUserDetailsService;
import com.project.services.UserService;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/users/public")
@CrossOrigin(origins = "http://localhost:3000")
public class UserPublicAPIs {

	// Notes --> download
	// Attendance --> all 
	// Shop --> add product, delete product
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
    private JpaUserDetailsService jpaUserDetailsService;
    
    @Autowired
    private UserService userService;

    @Autowired
    private JWTUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<String> authenticate(@RequestBody AuthenticationRequest request, HttpServletResponse response) {
        try {
            authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(request.getEnrollment(), request.getPassword(),
                            new ArrayList<>()));
            final UserDetails user = jpaUserDetailsService.loadUserByUsername(request.getEnrollment());
            if (user != null) {
                String jwt = jwtUtils.generateToken(user);
                Cookie cookie = new Cookie("jwt", jwt);
                cookie.setMaxAge(7 * 24 * 60 * 60); // expires in 7 days
//                cookie.setSecure(true);
                cookie.setHttpOnly(true);
                cookie.setPath("/"); // Global
                response.addCookie(cookie);
                return ResponseEntity.ok(jwt);
            }
            return ResponseEntity.status(400).body("Error authenticating");
        } catch (Exception e) {
            System.out.println(e);
            return ResponseEntity.status(400).body("" + e.getMessage());
        }
    }

    @PostMapping("/register")
    public ResponseEntity<ErrorResponse> register(@RequestBody UserDto userDto) throws Exception {
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
	         responseDto.setPassword(createUserDto.getPassword());
	         responseDto.setRoles(createUserDto.getRoles());
	         return ResponseEntity
						.status(HttpStatus.CREATED)
						.body(new ErrorResponse("User registered successfully!!"));
	    }
    }
    
}
