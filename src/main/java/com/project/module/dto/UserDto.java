package com.project.module.dto;

import lombok.Data;

@Data
public class UserDto {
	
	private long id;
	private String name;
	private String enrollment;
	private String password;
	private String branch;
	private int semester;
	private String roles;

}
