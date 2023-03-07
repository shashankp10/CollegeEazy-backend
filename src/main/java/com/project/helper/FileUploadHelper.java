package com.project.helper;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component
public class FileUploadHelper {
	
	//public final String UPLOAD_DIR = new ClassPathResource("static/file/").getFile().getAbsolutePath();
	
	//public FileUploadHelper() throws IOException{}
	public final String UPLOAD_DIR = "C:\\Users\\pande\\Documents\\workspace-spring-tool-suite-4-4.16.0.RELEASE\\Project-2\\src\\main\\resources\\static\\file";
	public boolean uploadFile(MultipartFile multiPartFile) {
		boolean f = false;
		
		try {
			
			Files.copy(multiPartFile.getInputStream(), 
					Paths.get(UPLOAD_DIR + File.separator + multiPartFile.getOriginalFilename()), 
						StandardCopyOption.REPLACE_EXISTING);
			f = true;
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return f;
	}
}
