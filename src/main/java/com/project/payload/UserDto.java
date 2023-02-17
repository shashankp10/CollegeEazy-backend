package com.project.payload;
 
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private long id;
	private String name;
	private String enrollment;
	private String password;
	private String branch;
	private int semester;
}
