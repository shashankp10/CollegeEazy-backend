package com.project.module.dto;

import lombok.Data;

@Data
public class NotesDto {
	
	private Long Id;
	private String subjectId;
	private String type;
	private String data;
	private String branch;
}
