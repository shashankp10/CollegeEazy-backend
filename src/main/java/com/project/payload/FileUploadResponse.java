package com.project.payload;

import lombok.Data;

@Data
public class FileUploadResponse {
	 	private String fileName;
	    private String downloadUri;
	    private long size;
}
