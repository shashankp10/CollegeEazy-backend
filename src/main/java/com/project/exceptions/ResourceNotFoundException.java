package com.project.exceptions;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	long fieldValue;
	String field;
	
	public ResourceNotFoundException(String resourceName, String fieldName, long subjectId) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, subjectId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = subjectId;
	}
	public ResourceNotFoundException(String resourceName, String fieldName, String subjectId) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, subjectId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.field = subjectId;
	}
}
